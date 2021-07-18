package com.example.utils.kafkaDelay;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dewey
 */
@Slf4j
public class KafkaDelayConsumerDamonThread extends Thread {

    private Map<String,KafkaDelayConsumerThread> consumerThreadsMap = new HashMap<>();

    private String consumerDamonStatus ;

    /**
     * 服务正常
     */
    public static final String STATUS_UP = "UP";

    /**
     * 部分消费线程不可用
     */
    public static final String STATUS_OUT_OF_SERVICE = "OUT_OF_SERVICE";


    public KafkaDelayConsumerDamonThread(Map<String,KafkaDelayConsumerThread> consumerThreadsMap) {
        this.consumerThreadsMap = consumerThreadsMap;
        this.consumerDamonStatus = STATUS_UP;
    }

    public String getConsumersStatus() {
        return consumerDamonStatus;
    }

    @Override
    public void run() {
        boolean up = true;

        while (true) {
            try {
                //check interval
                Thread.sleep(10000);

                if (log.isDebugEnabled()) {
                    log.debug("KafkaDelayConsumerManager:check consumer threads status");
                }

                for(Map.Entry<String,KafkaDelayConsumerThread> entry : consumerThreadsMap.entrySet()){
                    String topic = entry.getKey();
                    KafkaDelayConsumerThread consumerThread = entry.getValue();
                    if(consumerThread == null){
                        up = false;
                        log.error("KafkaDelayConsumerDamonThread:topic{},consumer thread exit",topic);
                        break;
                    }
                    if(consumerThread.getState() == State.TERMINATED){
                        up = false;
                        log.error("KafkaDelayConsumerDamonThread:topic{},consumer thread terminated",topic);
                        //consumerThread.start();
                    }
                }

                if(!up){
                    this.consumerDamonStatus = STATUS_OUT_OF_SERVICE;
                }

                if (log.isDebugEnabled()) {
                    log.debug("KafkaDelayConsumerManager:check consumer threads success");
                }

            } catch (Exception e) {
                log.error("KafkaDelayConsumerDamonThread error in check", e);
            }
        }
    }
}
