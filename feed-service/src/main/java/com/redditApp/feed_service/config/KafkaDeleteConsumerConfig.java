package com.redditApp.feed_service.config;

import com.redditApp.feed_service.event.PostDeletedEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaDeleteConsumerConfig {

    @Bean
    public ConsumerFactory<String, PostDeletedEvent> deleteConsumerFactory() {

        JsonDeserializer<PostDeletedEvent> deserializer =
                new JsonDeserializer<>(PostDeletedEvent.class, false);

        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "feed-service-delete");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostDeletedEvent>
    kafkaDeleteListenerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PostDeletedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(deleteConsumerFactory());

        return factory;
    }
}
