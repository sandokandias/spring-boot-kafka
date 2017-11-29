package com.github.sandokandias.spring.boot.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Event {

    private String id;
    private String name;
    private Context context;

    public static Event of(int index) {
        String id = String.valueOf(index);
        String name = String.format("Event-%s", id);
        String tenant = String.format("Tenant-%s", id);
        Context context = new Context(tenant);
        return new Event(id, name, context);
    }
}
