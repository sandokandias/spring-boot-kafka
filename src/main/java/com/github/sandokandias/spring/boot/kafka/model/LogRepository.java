package com.github.sandokandias.spring.boot.kafka.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class LogRepository {

    final ConcurrentLinkedQueue<Log> store = new ConcurrentLinkedQueue();

    public void add(Log log) {
        store.add(log);
    }

    public List<Log> findAll() {
        return new ArrayList<>(store);
    }
}
