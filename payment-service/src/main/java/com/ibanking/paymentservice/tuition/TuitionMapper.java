package com.ibanking.paymentservice.tuition;

import com.ibanking.paymentservice.student.StudentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = StudentMapper.class)
public interface TuitionMapper {
  Tuition dto2Model(TuitionReqDto dto);

  @Mapping(target = "student", qualifiedByName = "ignoreTuition")
  TuitionResDto model2Dto(Tuition topic);

  @Named("ignoreStudent")
  @Mapping(target = "student", ignore = true)
  TuitionResDto model2DtoIgnoreStudent(Tuition tuition);
}
