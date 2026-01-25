package com.redditApp.user_service.config;

import com.redditApp.user_service.events.UserSignedUpEvent;
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
    private static final String GROUP_ID = "user-service";

    @Bean
    public ConsumerFactory<String, UserSignedUpEvent> consumerFactory() {

        JsonDeserializer<UserSignedUpEvent> valueDeserializer =
                new JsonDeserializer<>(UserSignedUpEvent.class, false); // false = ignore type info headers
        valueDeserializer.addTrustedPackages("*"); // trust all packages

        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", valueDeserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserSignedUpEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserSignedUpEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

