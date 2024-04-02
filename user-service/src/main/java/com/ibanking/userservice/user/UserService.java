package com.ibanking.userservice.user;

import com.ibanking.userservice.auth0.Auth0Service;
import com.ibanking.userservice.exception.NotFoundException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;
  private final UserRepository repository;
  private final Auth0Service auth0Service;

  public User getById(String id, boolean noException) {
    User user = repository.findById(id).orElse(null);
    if (Objects.isNull(user) && !noException) {
      throw new NotFoundException(User.class, "id", id);
    }
    return user;
  }

  @Transactional
  public User getProfile(String userId) {
    User user = this.getById(userId, true);
    if (Objects.isNull(user)) {
      com.auth0.json.mgmt.users.User auth0User = auth0Service.getUserById(userId);
      user = userMapper.auth02Model(auth0User);
      user = repository.save(user);
    }
    return user;
  }

  public User updateById(String userId, UserReqDto dto) {
    User user = this.getById(userId, false);
    userMapper.updateModelFromDto(dto, user);
    return repository.save(user);
  }
}
