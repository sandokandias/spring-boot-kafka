package com.github.sandokandias.spring.boot.kafka.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class EventRepository {

    final ConcurrentLinkedQueue<Event> store = new ConcurrentLinkedQueue();

    public void add(Event event) {
        store.add(event);
    }

    public List<Event> findAll() {
        return new ArrayList<>(store);
    }
}
