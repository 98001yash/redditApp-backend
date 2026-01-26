package com.redditApp.feed_service.config;

import com.redditApp.event.PostCreatedEvent;
import com.redditApp.event.PostDeletedEvent;
import com.redditApp.event.PostUpdatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private Map<String, Object> baseProps() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "feed-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return props;
    }

    // -------- CREATED --------
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent>
    createdFactory() {

        JsonDeserializer<PostCreatedEvent> deserializer =
                new JsonDeserializer<>(PostCreatedEvent.class);

        deserializer.addTrustedPackages("*");

        DefaultKafkaConsumerFactory<String, PostCreatedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        baseProps(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PostCreatedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();

        container.setConsumerFactory(factory);

        return container;
    }

    // -------- UPDATED --------
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostUpdatedEvent>
    updatedFactory() {

        JsonDeserializer<PostUpdatedEvent> deserializer =
                new JsonDeserializer<>(PostUpdatedEvent.class);

        deserializer.addTrustedPackages("*");

        DefaultKafkaConsumerFactory<String, PostUpdatedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        baseProps(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PostUpdatedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();

        container.setConsumerFactory(factory);

        return container;
    }

    // -------- DELETED --------
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostDeletedEvent>
    deletedFactory() {

        JsonDeserializer<PostDeletedEvent> deserializer =
                new JsonDeserializer<>(PostDeletedEvent.class);

        deserializer.addTrustedPackages("*");

        DefaultKafkaConsumerFactory<String, PostDeletedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        baseProps(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PostDeletedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();

        container.setConsumerFactory(factory);

        return container;
    }
}
