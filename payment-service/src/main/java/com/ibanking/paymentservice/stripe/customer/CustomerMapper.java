package com.ibanking.paymentservice.stripe.customer;

import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
  CustomerCreateParams dto2CreateParams(CustomerReqDto dto);

  CustomerResDto model2Dto(Customer customer);
}
