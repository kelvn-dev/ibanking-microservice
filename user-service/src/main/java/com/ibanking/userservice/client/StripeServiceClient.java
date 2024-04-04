package com.ibanking.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service")
@Component
public interface StripeServiceClient {
  @PostMapping("/api/v1/stripe/customer")
  CustomerResDto createStripeCustomer(@RequestBody CustomerReqDto dto);

  @GetMapping("/api/v1/stripe/customer/{id}")
  CustomerResDto getStripeCustomer(@PathVariable(name = "id") String id);

  @PutMapping("/api/v1/stripe/customer/{id}")
  CustomerResDto updateStripeCustomer(
      @PathVariable(name = "id") String id, @RequestBody CustomerReqDto dto);
}
