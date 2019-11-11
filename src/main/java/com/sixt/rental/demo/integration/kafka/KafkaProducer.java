package com.sixt.rental.demo.integration.kafka;

import com.sixt.rental.demo.events.RawBookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, RawBookEvent> kafkaTemplate;

    @Value("${rental-service.topics.event-producer}")
    private String topicName;

    public ListenableFuture<SendResult<String, RawBookEvent>> send(RawBookEvent rawBookEvent) {
        var identifier = rawBookEvent.getIdentifier();
        var record = new ProducerRecord<>(topicName, identifier.getValue(), rawBookEvent);
        return kafkaTemplate.send(record);
    }
}
