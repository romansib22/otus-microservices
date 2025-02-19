package ru.otus.java.pro.mt.core.transfers.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransferMetricsService {
    private final Counter transferRequestsCounter;
    private final Counter transferSuccessCounter;
    private final Counter transferUnsuccessCounter;


    public TransferMetricsService(MeterRegistry meterRegistry) {
        transferRequestsCounter = Counter.builder("transfer_request_count")
                .description("Count of transfer requests")
                .tags("environment", "development")
                .register(meterRegistry);
        transferSuccessCounter = Counter.builder("success_transfer_count")
                .description("Count of success transfers")
                .tags("environment", "development")
                .register(meterRegistry);
        transferUnsuccessCounter = Counter.builder("unsuccess_transfer_count")
                .description("Count of unsuccess transfers")
                .tags("environment", "development")
                .register(meterRegistry);

    }

    public void incrementRequestsCount() {
        transferRequestsCounter.increment();
    }
    public void incrementSuccessCount() {
        transferSuccessCounter.increment();
    }
    public void incrementUnsuccessCount() {
        transferUnsuccessCounter.increment();
    }
}
