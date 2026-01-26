package com.redditApp.feed_service.config;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
//
//    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
//    private static final String GROUP_ID = "feed-service";
//
//    @Bean
//    public ConsumerFactory<String, PostCreatedEvent> consumerFactory() {
//
//        JsonDeserializer<PostCreatedEvent> valueDeserializer =
//                new JsonDeserializer<>(PostCreatedEvent.class, false); // false = ignore type info headers
//        valueDeserializer.addTrustedPackages("*"); // trust all packages
//
//        Map<String, Object> props = new HashMap<>();
//        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
//        props.put("group.id", GROUP_ID);
//        props.put("key.deserializer", StringDeserializer.class);
//        props.put("value.deserializer", valueDeserializer);
//
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
//    }
//
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {

        JsonDeserializer<Object> deserializer =
                new JsonDeserializer<>();

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(true);
        deserializer.setRemoveTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "feed-service");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}

