package com.github.sandokandias.spring.boot.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Log {

    private String msg;
    private Context context;

    public static Log of(int index) {
        String id = String.valueOf(index);
        String msg = String.format("Msg-%s", id);
        String tenant = String.format("Tenant-%s", id);
        Context context = new Context(tenant);
        return new Log(msg, context);
    }
}
