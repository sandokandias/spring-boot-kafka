package com.github.sandokandias.spring.boot.kafka.controller;


import com.github.sandokandias.spring.boot.kafka.model.Log;
import com.github.sandokandias.spring.boot.kafka.producer.LogProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.IntStream;

@RestController
public class LogController {

    final LogProducer logProducer;

    public LogController(LogProducer logProducer) {
        this.logProducer = logProducer;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/logs", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createLogs(@Valid @RequestBody CreateLogRequest request) {

        IntStream.range(0, request.getQuantity())
                .forEach(index -> {
                    Log log = Log.of(index);
                    logProducer.produce(log);
                });
    }
}
