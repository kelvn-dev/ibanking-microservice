package com.ibanking.paymentservice.stripe.customer;

import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final CustomerMapper customerMapper;

  @PostMapping
  public ResponseEntity<?> createCustomer(@RequestBody CustomerReqDto dto) {
    Customer customer = customerService.create(dto);
    return ResponseEntity.ok(customerMapper.model2Dto(customer));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCustomer(@PathVariable(name = "id") String id) {
    Customer customer = customerService.retrieve(id);
    return ResponseEntity.ok(customerMapper.model2Dto(customer));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCustomer(
      @PathVariable(name = "id") String id, @RequestBody CustomerReqDto dto) {
    Customer customer = customerService.update(id, dto);
    return ResponseEntity.ok(customerMapper.model2Dto(customer));
  }
}
