package com.ibanking.paymentservice.tuition;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface TuitionMapper {
  Tuition dto2Model(TuitionReqDto dto);

  TuitionResDto model2Dto(Tuition topic);

  @Named("ignoreStudent")
  @Mapping(target = "student", ignore = true)
  TuitionResDto model2DtoIgnoreStudent(Tuition tuition);
}
