package ru.otus.java.pro.mt.core.transfers.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;

@Repository
public interface TransfersPageRepository extends PagingAndSortingRepository<Transfer, String> {
    Page<Transfer> findAllByClientId(String clientId, Pageable p);

}
