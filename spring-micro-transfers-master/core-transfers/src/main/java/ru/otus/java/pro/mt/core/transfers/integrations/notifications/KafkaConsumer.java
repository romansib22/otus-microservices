package ru.otus.java.pro.mt.core.transfers.integrations.notifications;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;

import java.util.Objects;


@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${topic.transfers}")
    public void consume(ConsumerRecord<String, Transfer> transferRecord, @Headers MessageHeaders headers) {
        Acknowledgment ack = headers.get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        Transfer t = transferRecord.value();
        log.info("По переводу {} клиету {} отправлено уведомление: Произведён перевод клиенту {} на сумму {}", transferRecord.key(), t.getClientId(), t.getTargetClientId(), t.getAmount());
        if (Objects.nonNull(ack)) ack.acknowledge();
    }

    @DltHandler
    public void dlt(Transfer data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Event from topic {}  is dead lettered - event:{}", topic, data);
    }
}
