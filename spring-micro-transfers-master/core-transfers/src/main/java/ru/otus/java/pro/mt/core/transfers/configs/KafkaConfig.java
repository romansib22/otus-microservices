package ru.otus.java.pro.mt.core.transfers.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class KafkaConfig {
    @Value("${topic.transfers}")
    private String transfersTopic;

}
