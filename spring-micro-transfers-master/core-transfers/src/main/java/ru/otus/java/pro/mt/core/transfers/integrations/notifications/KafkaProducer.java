package ru.otus.java.pro.mt.core.transfers.integrations.notifications;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.configs.KafkaConfig;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;

import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    public void send(Transfer transfer) {
        try {
            kafkaTemplate.send(kafkaConfig.getTransfersTopic(), transfer.getId(), transfer).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Notification error", e);
        }
    }
}
