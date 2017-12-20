package com.github.sandokandias.spring.boot.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandokandias.spring.boot.kafka.model.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class LogProducer {

    final String topic;
    final KafkaTemplate<String, String> kafkaTemplate;
    final ProducerCallback producerCallback;
    final ObjectMapper objectMapper;

    public LogProducer(@Value("${kafka.topic.logs}") String topic,
                       KafkaTemplate<String, String> kafkaTemplate,
                       ProducerCallback producerCallback,
                       ObjectMapper objectMapper) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        this.producerCallback = producerCallback;
        this.objectMapper = objectMapper;
    }

    public void produce(Log logg) {

        log.info("Publishing log [{}]...", logg);
        try {
            String eventAsJson = objectMapper.writeValueAsString(logg);
            String key = UUID.randomUUID().toString();
            kafkaTemplate
                    .send(topic, key, eventAsJson)
                    .addCallback(producerCallback);
        } catch (Exception e) {
            log.error("Error publishing log [{}]", logg, e);
        }

    }
}
