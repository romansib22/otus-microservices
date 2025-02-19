package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.metrics.TransferMetricsService;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersPageRepository;
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
    private final TransferMetricsService transferMetricsService;
    private final TransfersPageRepository pageRepository;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    @Override
    public Page<Transfer> getAllTransfersPaginated(String clientId, int page, int limit) {
        Pageable transferPage = PageRequest.of(page, limit);
        return pageRepository.findAllByClientId(clientId, transferPage);
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        transferMetricsService.incrementRequestsCount();
        transferRequestValidator.validate(executeTransferDtoRq);
        // execution
        if (!limitsService.isLimitEnough()) {
            // ...
        }
        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
            transferMetricsService.incrementUnsuccessCount();
            throw new BusinessLogicException("OOPS", "OOPS_CODE");
        }
        Transfer transfer = new Transfer(UUID.randomUUID().toString(), "1", "2", "1", "2", "Demo", BigDecimal.ONE);
        save(transfer);
        transferMetricsService.incrementSuccessCount();
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }
}
