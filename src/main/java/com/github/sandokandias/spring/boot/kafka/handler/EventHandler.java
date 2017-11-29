package com.github.sandokandias.spring.boot.kafka.handler;

import com.github.sandokandias.spring.boot.kafka.model.Event;
import com.github.sandokandias.spring.boot.kafka.model.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class EventHandler {

    final ObjectMapper mapper;
    final EventRepository eventRepository;

    public EventHandler(ObjectMapper mapper, EventRepository eventRepository) {
        this.mapper = mapper;
        this.eventRepository = eventRepository;
    }

    public void handle(String key, String payload) {
        try {
            Event event = mapper.readValue(payload, Event.class);
            log.info("Processing event [{}]...", event);
            eventRepository.add(event);
            TimeUnit.SECONDS.sleep(1l);
            log.info("Event [{}] successfully processed.", event);
        } catch (IOException | InterruptedException e) {
            log.error("Error processing event with key [{}]", key, e);
        }
    }
}
