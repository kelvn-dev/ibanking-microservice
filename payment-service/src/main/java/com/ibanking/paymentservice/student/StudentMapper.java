package com.ibanking.paymentservice.student;

import com.ibanking.paymentservice.tuition.TuitionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TuitionMapper.class})
public interface StudentMapper {
  Student dto2Model(StudentReqDto dto);

  @Mapping(target = "tuition", qualifiedByName = "ignoreStudent")
  StudentResDto model2Dto(Student student);
}
