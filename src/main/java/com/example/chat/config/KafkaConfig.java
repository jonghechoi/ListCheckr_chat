package com.example.chat.config;

import com.example.chat.domain.Message;
import com.google.common.collect.ImmutableMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String bootStrapServers = "bootstrap-servers";
    private Map<String, String> producer = new HashMap<>();
    private Map<String, String> consumer = new HashMap<>();

    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getProducerProps());
    }

    @Bean
    public Map<String, Object> getProducerProps() {
        return ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
                .build();
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(3);
//        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getConsumerProps());
    }

    @Bean
    public Map<String, Object> getConsumerProps() {
        return ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .put("group.id", "chat")
//                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.consumer.get("auto.offset.reset"))
//                .put("group.id", this.consumer.get("group.id"))
                .build();
    }


//    public Map<String, Object> getConsumerProps() {
//        Map<String, Object> properties = new HashMap<>(this.consumer);
//        if (!properties.containsKey("bootstrap-servers")) {
//            properties.put("bootstrap-servers", this.bootStrapServers);
//        }
//        properties.put("key.serializer", this.consumer.get("key.serializer"));
//        properties.put("value.serializer", this.consumer.get("value.serializer"));
//        properties.put("group.id", this.consumer.get("group.id"));
//        properties.put("auto.offset.reset", this.consumer.get("auto.offset.reset"));
//        return properties;
//    }
}