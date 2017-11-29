package com.github.sandokandias.spring.boot.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ProducerCallback implements ListenableFutureCallback<SendResult<String, String>> {

    @Override
    public void onFailure(Throwable throwable) {
        log.error("Error publishing event.",
                throwable);
    }

    @Override
    public void onSuccess(SendResult<String, String> result) {
        String key = result.getProducerRecord().key();
        String event = result.getProducerRecord().value();
        String topic = result.getRecordMetadata().topic();
        log.info("[key: {}, event: {}] successfully published in the topic [{}].",
                key,
                event,
                topic);
    }
}