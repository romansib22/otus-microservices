package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.integrations.notifications.KafkaProducer;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final TransferRequestValidator transferRequestValidator;
    private final TransfersProperties transfersProperties;
    private final LimitsServiceImpl limitsService;
    private final KafkaProducer kafkaProducer;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        transferRequestValidator.validate(executeTransferDtoRq);
        // execution
        if (!limitsService.isLimitEnough()) {
            // ...
        }
        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
            throw new BusinessLogicException("OOPS", "OOPS_CODE");
        }
        Transfer transfer = new Transfer(UUID.randomUUID().toString(), clientId, executeTransferDtoRq.getTargetClientId(), "1", "2", executeTransferDtoRq.getMessage(), executeTransferDtoRq.getAmount());
        save(transfer);
        kafkaProducer.send(transfer);
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }
}
