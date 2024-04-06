package com.ibanking.paymentservice;

import com.ibanking.paymentservice.student.Student;
import com.ibanking.paymentservice.student.StudentRepository;
import com.ibanking.paymentservice.tuition.SemesterCode;
import com.ibanking.paymentservice.tuition.Tuition;
import com.ibanking.paymentservice.tuition.TuitionRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  @Value("${server.init-data}")
  private Boolean initData;

  private final StudentRepository studentRepository;
  private final TuitionRepository tuitionRepository;

  @Override
  public void run(String... args) throws Exception {
    if (!initData) {
      return;
    }
    int leftLimit = 52100000;
    int rightLimit = 52100099;
    List<Student> students = new ArrayList<>();
    for (int i = leftLimit; i <= rightLimit; i++) {
      Student student =
          Student.builder().studentId(String.valueOf(i)).fullName("Student" + i).build();
      students.add(student);
    }
    students = studentRepository.saveAll(students);

    List<Tuition> tuitions = new ArrayList<>();
    students.forEach(
        student -> {
          for (short i = 2023; i <= 2024; i++) {
            Tuition.TuitionBuilder tuition =
                Tuition.builder().studentId(student.getId()).semesterYear(i).charges(i);
            tuitions.add(tuition.semesterCode(SemesterCode.FIRST).build());
            tuitions.add(tuition.semesterCode(SemesterCode.SECOND).build());
            tuitions.add(tuition.semesterCode(SemesterCode.THIRD).build());
          }
        });
    tuitionRepository.saveAll(tuitions);
  }
}
