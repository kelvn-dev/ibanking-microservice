package com.ibanking.paymentservice.stripe.customer;

import com.ibanking.paymentservice.stripe.StripePropConfig;
import com.ibanking.paymentservice.stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends StripeService {

  private final CustomerMapper customerMapper;

  public CustomerService(StripePropConfig stripePropConfig, CustomerMapper customerMapper) {
    super(stripePropConfig);
    this.customerMapper = customerMapper;
  }

  @SneakyThrows(StripeException.class)
  public Customer create(CustomerReqDto dto) {
    CustomerCreateParams params = customerMapper.dto2CreateParams(dto);
    return Customer.create(params, requestOptions);
  }

  @SneakyThrows(StripeException.class)
  public Customer retrieve(String id) {
    return Customer.retrieve(id, requestOptions);
  }

  @SneakyThrows(StripeException.class)
  public Customer update(String id, CustomerReqDto dto) {
    Customer customer = this.retrieve(id);
    CustomerUpdateParams params =
        CustomerUpdateParams.builder().setName(dto.getName()).setPhone(dto.getPhone()).build();
    return customer.update(params, requestOptions);
  }

  @SneakyThrows(StripeException.class)
  public Customer decreaseBalance(Customer customer, long amount) {
    CustomerUpdateParams params =
        CustomerUpdateParams.builder().setBalance(customer.getBalance() - amount).build();
    return customer.update(params, requestOptions);
  }
}
