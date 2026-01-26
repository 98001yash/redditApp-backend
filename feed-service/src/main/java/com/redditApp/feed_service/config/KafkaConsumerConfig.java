package com.redditApp.feed_service.config;


import com.redditApp.feed_service.event.PostCreatedEvent;
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

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "feed-service";

    @Bean
    public ConsumerFactory<String, PostCreatedEvent> consumerFactory() {

        JsonDeserializer<PostCreatedEvent> valueDeserializer =
                new JsonDeserializer<>(PostCreatedEvent.class, false); // false = ignore type info headers
        valueDeserializer.addTrustedPackages("*"); // trust all packages

        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", valueDeserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

//@Configuration
//@EnableKafka
//public class KafkaConsumerConfig {
//
//    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
//    private static final String GROUP_ID = "feed-service";
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//
//        Map<String, Object> props = new HashMap<>();
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                BOOTSTRAP_SERVERS);
//
//        props.put(ConsumerConfig.GROUP_ID_CONFIG,
//                GROUP_ID);
//
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                ErrorHandlingDeserializer.class);
//
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                ErrorHandlingDeserializer.class);
//
//        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS,
//                StringDeserializer.class);
//
//        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
//                JsonDeserializer.class);
//
//        props.put(JsonDeserializer.TRUSTED_PACKAGES,
//                "com.redditApp.post_service.event");
//
//        // IMPORTANT: enable type headers
//        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object>
//    kafkaListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setConsumerFactory(consumerFactory());
//
//        return factory;
//    }
//}

