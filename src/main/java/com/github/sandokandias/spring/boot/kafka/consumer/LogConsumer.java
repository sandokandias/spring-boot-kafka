package com.github.sandokandias.spring.boot.kafka.consumer;

import com.github.sandokandias.spring.boot.kafka.handler.LogHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogConsumer {

    final LogHandler logHandler;

    public LogConsumer(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    @KafkaListener(topics = "${kafka.topic.logs}")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                       @Payload String payload) {
        log.info("Consuming log {}", payload);
        logHandler.handle(key, payload);
    }
}
