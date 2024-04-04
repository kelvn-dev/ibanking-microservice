package com.ibanking.paymentservice.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
@Component
public interface UserServiceClient {
  @GetMapping("/api/v1/users")
  UserResDto getUser(@RequestHeader(name = "x-user-id") String userId);
}
