package com.ibanking.paymentservice.student;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, UUID> {
  Optional<Student> findByStudentIdIgnoreCase(String studentId);
}
