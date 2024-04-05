package com.ibanking.paymentservice.student;

import com.ibanking.paymentservice.tuition.TuitionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = {TuitionMapper.class})
public interface StudentMapper {
  Student dto2Model(StudentReqDto dto);

  @Mapping(target = "tuition", qualifiedByName = "ignoreStudent")
  StudentResDto model2Dto(Student student);

  @Named("ignoreTuition")
  @Mapping(target = "tuition", ignore = true)
  StudentResDto model2DtoIgnoreTuition(Student student);
}
