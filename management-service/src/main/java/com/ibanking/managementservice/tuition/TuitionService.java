package com.ibanking.managementservice.tuition;

import com.ibanking.managementservice.common.BaseService;
import com.ibanking.managementservice.exception.ConflictException;
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
}
