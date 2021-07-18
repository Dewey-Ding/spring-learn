package com.example.aop.utils;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.requests.IsolationLevel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.time.Duration;
import java.util.*;

/**
 * 自定义kafka配置
 * 仅用作consumer
 * @author dewey
 */
@ConfigurationProperties(prefix = "kafka.delay")
@Configuration
public class KafkaDelayConfig {

    @Autowired(required = false)
    private KafkaProperties kafkaProperties;

    private Consumer consumer = new Consumer();

    private List<String> bootstrapServers = new ArrayList<>(Collections.singletonList("localhost:9092"));

    private String clientId;



    /**
     * 获取消费端配置
     */
    public Map<String,Object> getConsumerProperties(){
        Map<String,Object> properties = this.getSpringKafkaConfig();
        properties.putAll(this.buildCommonProperties());
        properties.putAll(this.consumer.buildProperties());
        return properties;
    }

    /**
     * 集成spring-kafka工程的先获取默认配置
     * @return
     */
    public Map<String, Object> getSpringKafkaConfig(){
        Map<String,Object> properties = new HashMap<>();
        if(kafkaProperties != null){
            return kafkaProperties.buildConsumerProperties();
        } else{
            return properties;
        }
    }

    private Map<String, Object> buildCommonProperties() {
        Map<String, Object> properties = new HashMap<>();
        if (this.bootstrapServers != null) {
            properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        }
        if (this.clientId != null) {
            properties.put(CommonClientConfigs.CLIENT_ID_CONFIG, this.clientId);
        }
        return properties;
    }

    public static class Consumer {

        /**
         * Frequency with which the consumer offsets are auto-committed to Kafka if
         * 'enable.auto.commit' is set to true.
         */
        private Duration autoCommitInterval;

        /**
         * What to do when there is no initial offset in Kafka or if the current offset no
         * longer exists on the server.
         */
        private String autoOffsetReset;

        /**
         * Comma-delimited list of host:port pairs to use for establishing the initial
         * connections to the Kafka cluster. Overrides the global property, for consumers.
         */
        private List<String> bootstrapServers;

        /**
         * ID to pass to the server when making requests. Used for server-side logging.
         */
        private String clientId;

        /**
         * Whether the consumer's offset is periodically committed in the background.
         */
        private Boolean enableAutoCommit;

        /**
         * Maximum amount of time the server blocks before answering the fetch request if
         * there isn't sufficient data to immediately satisfy the requirement given by
         * "fetch-min-size".
         */
        private Duration fetchMaxWait;

        /**
         * Minimum amount of data the server should return for a fetch request.
         */
        private DataSize fetchMinSize;

        /**
         * Unique string that identifies the consumer group to which this consumer
         * belongs.
         */
        private String groupId;

        /**
         * Expected time between heartbeats to the consumer coordinator.
         */
        private Duration heartbeatInterval;

        /**
         * Isolation level for reading messages that have been written transactionally.
         */
        private IsolationLevel isolationLevel = IsolationLevel.READ_UNCOMMITTED;

        /**
         * Deserializer class for keys.
         */
        private Class<?> keyDeserializer = StringDeserializer.class;

        /**
         * Deserializer class for values.
         */
        private Class<?> valueDeserializer = StringDeserializer.class;

        /**
         * Maximum number of records returned in a single call to poll().
         */
        private Integer maxPollRecords;

        /**
         * Additional consumer-specific properties used to configure the client.
         */
        private final Map<String, String> properties = new HashMap<>();

        public Duration getAutoCommitInterval() {
            return this.autoCommitInterval;
        }

        public void setAutoCommitInterval(Duration autoCommitInterval) {
            this.autoCommitInterval = autoCommitInterval;
        }

        public String getAutoOffsetReset() {
            return this.autoOffsetReset;
        }

        public void setAutoOffsetReset(String autoOffsetReset) {
            this.autoOffsetReset = autoOffsetReset;
        }

        public List<String> getBootstrapServers() {
            return this.bootstrapServers;
        }

        public void setBootstrapServers(List<String> bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public String getClientId() {
            return this.clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public Boolean getEnableAutoCommit() {
            return this.enableAutoCommit;
        }

        public void setEnableAutoCommit(Boolean enableAutoCommit) {
            this.enableAutoCommit = enableAutoCommit;
        }

        public Duration getFetchMaxWait() {
            return this.fetchMaxWait;
        }

        public void setFetchMaxWait(Duration fetchMaxWait) {
            this.fetchMaxWait = fetchMaxWait;
        }

        public DataSize getFetchMinSize() {
            return this.fetchMinSize;
        }

        public void setFetchMinSize(DataSize fetchMinSize) {
            this.fetchMinSize = fetchMinSize;
        }

        public String getGroupId() {
            return this.groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public Duration getHeartbeatInterval() {
            return this.heartbeatInterval;
        }

        public void setHeartbeatInterval(Duration heartbeatInterval) {
            this.heartbeatInterval = heartbeatInterval;
        }

        public IsolationLevel getIsolationLevel() {
            return this.isolationLevel;
        }

        public void setIsolationLevel(IsolationLevel isolationLevel) {
            this.isolationLevel = isolationLevel;
        }

        public Class<?> getKeyDeserializer() {
            return this.keyDeserializer;
        }

        public void setKeyDeserializer(Class<?> keyDeserializer) {
            this.keyDeserializer = keyDeserializer;
        }

        public Class<?> getValueDeserializer() {
            return this.valueDeserializer;
        }

        public void setValueDeserializer(Class<?> valueDeserializer) {
            this.valueDeserializer = valueDeserializer;
        }

        public Integer getMaxPollRecords() {
            return this.maxPollRecords;
        }

        public void setMaxPollRecords(Integer maxPollRecords) {
            this.maxPollRecords = maxPollRecords;
        }

        public Map<String, String> getProperties() {
            return this.properties;
        }

        public Map<String, Object> buildProperties() {
            Map<String, Object> properties = new HashMap<>();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(this::getAutoCommitInterval).asInt(Duration::toMillis)
                    .to(v->properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,v));
            map.from(this::getAutoOffsetReset).to(v->properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,v));
            map.from(this::getBootstrapServers).to(v->properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,v));
            map.from(this::getClientId).to(v->properties.put(ConsumerConfig.CLIENT_ID_CONFIG,v));
            map.from(this::getEnableAutoCommit).to(v->properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,v));
            map.from(this::getFetchMaxWait).asInt(Duration::toMillis)
                    .to(v->properties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,v));
            map.from(this::getFetchMinSize).asInt(DataSize::toBytes)
                    .to(v->properties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG,v));
            map.from(this::getGroupId).to(v->properties.put(ConsumerConfig.GROUP_ID_CONFIG,v));
            map.from(this::getHeartbeatInterval).asInt(Duration::toMillis)
                    .to(v->properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,v));
            map.from(() -> getIsolationLevel().name().toLowerCase(Locale.ROOT))
                    .to(v->properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,v));
            map.from(this::getKeyDeserializer).to(v->properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,v));
            map.from(this::getValueDeserializer).to(v->properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,v));
            map.from(this::getMaxPollRecords).to(v->properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,v));
            return properties;
        }
    }

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public KafkaProperties getKafkaProperties() {
        return kafkaProperties;
    }

    public void setKafkaProperties(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
