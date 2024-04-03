package com.ibanking.paymentservice.tuition;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface TuitionRepository extends JpaRepository<Tuition, UUID> {
  @Lock(LockModeType.PESSIMISTIC_READ)
  Tuition getById(UUID id);

  Optional<Tuition> findByStudentIdAndSemesterYearAndSemesterCode(
      UUID studentId, short semesterYear, SemesterCode semesterCode);
}
