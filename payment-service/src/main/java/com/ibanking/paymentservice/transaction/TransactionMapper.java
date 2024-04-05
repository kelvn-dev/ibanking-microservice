package com.ibanking.paymentservice.transaction;

import com.ibanking.paymentservice.tuition.TuitionMapper;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = TuitionMapper.class)
public interface TransactionMapper {
  @Mapping(target = "tuition", ignore = true)
  TransactionResDto model2DtoIgnoreTuition(Transaction transaction);

  @Named("includeTuition")
  TransactionResDto model2Dto(Transaction transaction);

  @IterableMapping(qualifiedByName = "includeTuition")
  List<TransactionResDto> model2Dto(List<Transaction> transaction);
}
