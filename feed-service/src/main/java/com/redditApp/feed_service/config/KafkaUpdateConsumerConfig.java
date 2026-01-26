package com.redditApp.feed_service.config;

import com.redditApp.feed_service.event.PostUpdatedEvent;
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
public class KafkaUpdateConsumerConfig {

    @Bean
    public ConsumerFactory<String, PostUpdatedEvent> updateConsumerFactory() {

        JsonDeserializer<PostUpdatedEvent> deserializer =
                new JsonDeserializer<>(PostUpdatedEvent.class, false);

        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "feed-service-update");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostUpdatedEvent>
    kafkaUpdateListenerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PostUpdatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(updateConsumerFactory());

        return factory;
    }
}
