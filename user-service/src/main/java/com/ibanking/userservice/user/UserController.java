package com.ibanking.userservice.user;

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

  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader("x-user-id") String userId) {
    User user = userService.getProfile(userId);
    return ResponseEntity.ok(userMapper.model2Dto(user));
  }

  @PutMapping("/profile")
  public ResponseEntity<?> updateProfile(
      @RequestHeader("x-user-id") String userId, @Valid @RequestBody UserReqDto dto) {
    User user = userService.updateById(userId, dto);
    return ResponseEntity.ok(userMapper.model2Dto(user));
  }
}
