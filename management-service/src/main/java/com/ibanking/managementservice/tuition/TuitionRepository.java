package com.ibanking.managementservice.tuition;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuitionRepository extends JpaRepository<Tuition, UUID> {
  Optional<Tuition> findByStudentIdAndSemesterYearAndSemesterCode(
      UUID studentId, short semesterYear, SemesterCode semesterCode);
}
