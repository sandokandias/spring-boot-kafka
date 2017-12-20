package com.github.sandokandias.spring.boot.kafka.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandokandias.spring.boot.kafka.model.Log;
import com.github.sandokandias.spring.boot.kafka.model.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LogHandler {

    final ObjectMapper mapper;
    final LogRepository logRepository;

    public LogHandler(ObjectMapper mapper, LogRepository logRepository) {
        this.mapper = mapper;
        this.logRepository = logRepository;
    }

    public void handle(String key, String payload) {
        try {
            Log logg = mapper.readValue(payload, Log.class);
            log.info("Processing log [{}]...", logg);
            logRepository.add(logg);
            TimeUnit.SECONDS.sleep(1l);
            log.info("Log [{}] successfully processed.", logg);
        } catch (IOException | InterruptedException e) {
            log.error("Error processing log with key [{}]", key, e);
        }
    }
}
