package com.ibanking.paymentservice.transaction;

import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
  TransactionResDto model2Dto(Transaction transaction);
}
