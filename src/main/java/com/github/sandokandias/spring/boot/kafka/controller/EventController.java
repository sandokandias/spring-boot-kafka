package com.github.sandokandias.spring.boot.kafka.controller;


import com.github.sandokandias.spring.boot.kafka.model.Event;
import com.github.sandokandias.spring.boot.kafka.producer.EventProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.IntStream;

@RestController(value = "/events")
public class EventController {

    final EventProducer eventProducer;

    public EventController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createEvents(@Valid @RequestBody CreateEventRequest request) {

        IntStream.range(0, request.getQuantity())
                .forEach(index -> {
                    Event event = Event.of(index);
                    eventProducer.produce(event);
                });
    }
}
