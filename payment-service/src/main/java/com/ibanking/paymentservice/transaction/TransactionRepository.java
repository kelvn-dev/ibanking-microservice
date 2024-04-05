package com.ibanking.paymentservice.transaction;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import jakarta.persistence.LockModeType;
import java.util.UUID;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends EntityGraphJpaRepository<Transaction, UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Transaction getById(UUID id);

  Iterable<Transaction> findAllByTuitionIdAndUserId(UUID tuitionId, String userId);

  Iterable<Transaction> findAllByUserIdAndStatus(
      String userId, TransactionStatus status, EntityGraph entityGraph);
}
