package com.ibanking.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", path = "/api/v1/stripe")
@Component
public interface StripeServiceClient {
  @PostMapping("/customer")
  CustomerResDto createStripeCustomer(@RequestBody CustomerReqDto dto);

  @GetMapping("/customer/{id}")
  CustomerResDto getStripeCustomer(@PathVariable(name = "id") String id);

  @PutMapping("/customer/{id}")
  CustomerResDto updateStripeCustomer(
      @PathVariable(name = "id") String id, @RequestBody CustomerReqDto dto);
}
