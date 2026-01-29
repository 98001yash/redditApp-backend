package com.redditApp.notification_service.config;

import com.redditApp.event.PostVotedEvent;
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

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "notification-service");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostVotedEvent>
    voteFactory() {

        JsonDeserializer<PostVotedEvent> deserializer =
                new JsonDeserializer<>(PostVotedEvent.class);

        deserializer.addTrustedPackages("*");

        DefaultKafkaConsumerFactory<String, PostVotedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        baseProps(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PostVotedEvent> container =
                new ConcurrentKafkaListenerContainerFactory<>();

        container.setConsumerFactory(factory);

        return container;
    }
}
