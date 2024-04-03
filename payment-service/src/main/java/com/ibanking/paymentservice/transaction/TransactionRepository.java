package com.ibanking.paymentservice.transaction;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Transaction getById(UUID id);

  List<Transaction> findAllByTuitionIdAndUserId(UUID tuitionId, String userId);
}
