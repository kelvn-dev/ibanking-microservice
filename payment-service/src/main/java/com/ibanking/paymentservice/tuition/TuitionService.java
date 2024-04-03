package com.ibanking.paymentservice.tuition;

import com.ibanking.paymentservice.common.BaseService;
import com.ibanking.paymentservice.exception.ConflictException;
import com.ibanking.paymentservice.exception.NotFoundException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TuitionService extends BaseService<Tuition, TuitionRepository> {

  private final TuitionMapper tuitionMapper;

  public TuitionService(TuitionRepository repository, TuitionMapper tuitionMapper) {
    super(repository);
    this.tuitionMapper = tuitionMapper;
  }

  public Tuition create(TuitionReqDto dto) {
    if (repository
        .findByStudentIdAndSemesterYearAndSemesterCode(
            dto.getStudentId(), dto.getSemesterYear(), dto.getSemesterCode())
        .isPresent()) {
      throw new ConflictException(
          modelClass,
          "studentId",
          dto.getStudentId().toString(),
          "semesterYear",
          String.valueOf(dto.getSemesterYear()),
          "semesterCode",
          dto.getSemesterCode().toString());
    }
    Tuition tuition = tuitionMapper.dto2Model(dto);
    return repository.save(tuition);
  }

  public Tuition getByIdForUpdate(UUID id) {
    Tuition tuition = repository.getById(id);
    if (Objects.isNull(tuition)) {
      throw new NotFoundException(modelClass, "id", id.toString());
    }
    return tuition;
  }
}
