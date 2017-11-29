package com.github.sandokandias.spring.boot.kafka.producer;

import com.github.sandokandias.spring.boot.kafka.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventProducer {

    final String topic;
    final KafkaTemplate<String, String> kafkaTemplate;
    final ProducerCallback producerCallback;
    final ObjectMapper objectMapper;

    public EventProducer(@Value("${kafka.topic.events}") String topic,
                         KafkaTemplate<String, String> kafkaTemplate,
                         ProducerCallback producerCallback,
                         ObjectMapper objectMapper) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        this.producerCallback = producerCallback;
        this.objectMapper = objectMapper;
    }

    public void produce(Event event) {

        log.info("Publishing event [{}]...", event);
        String key = event.getId();
        try {
            String eventAsJson = objectMapper.writeValueAsString(event);
            kafkaTemplate
                    .send(topic, key, eventAsJson)
                    .addCallback(producerCallback);
        } catch (Exception e) {
            log.error("Error publishing event with key [{}]", key, e);
        }

    }
}
