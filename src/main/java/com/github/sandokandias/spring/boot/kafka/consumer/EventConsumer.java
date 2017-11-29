package com.github.sandokandias.spring.boot.kafka.consumer;

import com.github.sandokandias.spring.boot.kafka.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventConsumer {

    final EventHandler eventHandler;

    public EventConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "${kafka.topic.events}")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                       @Payload String payload) {
        eventHandler.handle(key, payload);
    }
}
