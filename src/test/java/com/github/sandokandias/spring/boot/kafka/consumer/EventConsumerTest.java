package com.github.sandokandias.spring.boot.kafka.consumer;

import com.github.sandokandias.spring.boot.kafka.model.Event;
import com.github.sandokandias.spring.boot.kafka.model.EventRepository;
import com.github.sandokandias.spring.boot.kafka.model.Log;
import com.github.sandokandias.spring.boot.kafka.model.LogRepository;
import com.github.sandokandias.spring.boot.kafka.producer.EventProducer;
import com.github.sandokandias.spring.boot.kafka.producer.LogProducer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public class EventConsumerTest {

    static final int BROKERS = 1;
    static final boolean CONTROLLED_SHUTDOWN = true;
    static final int PARTITIONS = 2;
    static final String TOPIC_EVENTS = "events";
    static final String TOPIC_LOGS = "logs";

    @Autowired
    EventProducer eventProducer;

    @Autowired
    LogProducer logProducer;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(BROKERS, CONTROLLED_SHUTDOWN, PARTITIONS, TOPIC_EVENTS, TOPIC_LOGS);

    @Before
    public void setUp() throws Exception {

        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                .getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer,
                    embeddedKafka.getPartitionsPerTopic());
        }
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        Map<String, Object> senderProperties =
                KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());

        ProducerFactory<String, String> producerFactory =
                new DefaultKafkaProducerFactory<>(senderProperties);

        return new KafkaTemplate<>(producerFactory);
    }

    @Test
    public void shouldConsume() throws InterruptedException {

        IntStream.range(0, 2)
                .forEach(index -> {
                    Event event = Event.of(index);
                    eventProducer.produce(event);

                    Log log = Log.of(index);
                    logProducer.produce(log);

                });

        TimeUnit.SECONDS.sleep(5l);

        List<Event> events = eventRepository.findAll();
        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals(2, events.size());

        List<Log> logs = logRepository.findAll();
        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        assertEquals(2, logs.size());
    }


}
