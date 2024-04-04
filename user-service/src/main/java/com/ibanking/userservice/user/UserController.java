package com.ibanking.userservice.user;

import com.ibanking.userservice.client.CustomerResDto;
import com.ibanking.userservice.client.StripeServiceClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;
  private final StripeServiceClient stripeServiceClient;

  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader("x-user-id") String userId) {
    User user = userService.getProfile(userId);
    CustomerResDto stripeCustomer = stripeServiceClient.getStripeCustomer(user.getStripeCustomer());
    UserResDto dto = userMapper.model2Dto(user);
    dto.setBalances(stripeCustomer.getBalance());
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/profile")
  public ResponseEntity<?> updateProfile(
      @RequestHeader("x-user-id") String userId, @Valid @RequestBody UserReqDto dto) {
    userService.updateById(userId, dto);
    return ResponseEntity.ok(null);
  }
}
