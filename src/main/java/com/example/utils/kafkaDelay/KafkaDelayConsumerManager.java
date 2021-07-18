package com.example.utils.kafkaDelay;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dewey
 */
@Slf4j
public class KafkaDelayConsumerManager implements BeanPostProcessor, SmartInitializingSingleton {
    /**
     * 延迟消费实例集合
     */
    private List<KafkaDelayConsumerContainer> consumerContainers = new ArrayList<>();

    private KafkaDelayConsumerDamonThread damonThread;

    @Autowired
    private KafkaDelayConfig kafkaDelayConfig;

    /**
     * 健康检查
     * @return
     */
    public Status health(){
        if(CollectionUtils.isEmpty(consumerContainers)) {
            return Status.UP;
        }
        Thread.State state = damonThread.getState();
        if(state == Thread.State.TERMINATED){
            return Status.DOWN;
        }
        if(damonThread.getConsumersStatus().equals(KafkaDelayConsumerDamonThread.STATUS_UP)){
            return Status.UP;
        }
        return Status.OUT_OF_SERVICE;
    }

    @Override
    public void afterSingletonsInstantiated() {
        //init consumer threads
        if(!CollectionUtils.isEmpty(consumerContainers)) {
            Map<String,KafkaDelayConsumerThread> consumerThreadsMap = new HashMap<>();

            for (KafkaDelayConsumerContainer consumerContainer : consumerContainers) {
                KafkaConsumer<String, String> consumer = createConsumer(consumerContainer);
                consumerContainer.setConsumer(consumer);

                String topic = consumerContainer.getKafkaDelayListener().topic();
                KafkaDelayConsumerThread consumerThread = new KafkaDelayConsumerThread(consumerContainer);
                consumerThread.start();

                consumerThreadsMap.put(topic,consumerThread);
                log.info("KafkaDelayConsumerManager:start consume kafka topic {}", topic);
            }

            if (!CollectionUtils.isEmpty(consumerContainers)) {
                KafkaDelayConsumerDamonThread damon = new KafkaDelayConsumerDamonThread(consumerThreadsMap);
                damon.setDaemon(true);
                damon.start();
                log.info("KafkaDelayConsumerManager:start kafka delay daemon");

                damonThread = damon;
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName){
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Map<Method, KafkaDelayListener> temp = MethodIntrospector.selectMethods(targetClass,
                new MethodIntrospector.MetadataLookup<KafkaDelayListener>() {
                    @Override
                    public KafkaDelayListener inspect(Method method) {
                        KafkaDelayListener ann = AnnotationUtils.findAnnotation(method, KafkaDelayListener.class);
                        return ann;
                    }

                });
        if(!CollectionUtils.isEmpty(temp)){
            for (Map.Entry<Method,KafkaDelayListener> entry:temp.entrySet()){
                consumerContainers.add(new KafkaDelayConsumerContainer(bean,entry.getKey(),entry.getValue()));
            }

        }
        return bean;
    }

    /**
     * @param consumerContainer
     * @return
     */
    private KafkaConsumer<String,String> createConsumer(KafkaDelayConsumerContainer consumerContainer){
        Map<String,Object> map = kafkaDelayConfig.getConsumerProperties();
        Boolean autoCommit = (Boolean) map.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG);
        if(consumerContainer.getListenerType().equals(KafkaDelayListenerType.SIMPLE)){
            if(autoCommit != null && !autoCommit){
                throw new RuntimeException(String.format("KafkaDelayConsumerManager:should manual submission in method %s",
                        consumerContainer.getMethod().getName()));
            }
        }else if(autoCommit == null || autoCommit){
            throw new RuntimeException("KafkaDelayConsumerManager:autocommit should be false ");
        }
        return new KafkaConsumer<String, String>(map);
    }


}