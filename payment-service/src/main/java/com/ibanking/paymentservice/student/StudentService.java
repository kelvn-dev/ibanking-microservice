package com.ibanking.paymentservice.student;

import com.ibanking.paymentservice.common.BaseService;
import com.ibanking.paymentservice.exception.ConflictException;
import com.ibanking.paymentservice.exception.NotFoundException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends BaseService<Student, StudentRepository> {

  private final StudentMapper studentMapper;

  public StudentService(StudentRepository repository, StudentMapper studentMapper) {
    super(repository);
    this.studentMapper = studentMapper;
  }

  public Student create(StudentReqDto dto) {
    if (repository.findByStudentIdIgnoreCase(dto.getStudentId()).isPresent()) {
      throw new ConflictException(modelClass, "studentId", dto.getStudentId());
    }
    Student student = studentMapper.dto2Model(dto);
    return repository.save(student);
  }

  public Student getByStudentId(String studentId, boolean noException) {
    Student student = repository.findByStudentIdIgnoreCase(studentId).orElse(null);
    if (Objects.isNull(student) && !noException) {
      throw new NotFoundException(modelClass, "studentId", studentId);
    }
    return student;
  }
}
