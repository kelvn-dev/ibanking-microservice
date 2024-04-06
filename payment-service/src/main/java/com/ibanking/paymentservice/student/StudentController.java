package com.ibanking.paymentservice.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
  private final StudentService studentService;
  private final StudentMapper studentMapper;

  @GetMapping("/{studentId}")
  public ResponseEntity<?> getById(@PathVariable(name = "studentId") String studentId) {
    Student student = studentService.getByStudentId(studentId, false);
    return ResponseEntity.ok(studentMapper.model2Dto(student));
  }
}
