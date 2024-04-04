package com.ibanking.userservice.user;

import com.ibanking.userservice.auth0.Auth0Service;
import com.ibanking.userservice.client.CustomerReqDto;
import com.ibanking.userservice.client.CustomerResDto;
import com.ibanking.userservice.client.StripeServiceClient;
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
  private final StripeServiceClient stripeServiceClient;

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
      user = getFromAuth0User(userId);
      String stripeCustomer = createStripeCustomer(user);
      user.setStripeCustomer(stripeCustomer);
      user = repository.save(user);
    }
    return user;
  }

  private User getFromAuth0User(String userId) {
    com.auth0.json.mgmt.users.User auth0User = auth0Service.getUserById(userId);
    return userMapper.auth02Model(auth0User);
  }

  private String createStripeCustomer(User user) {
    CustomerReqDto dto = userMapper.model2StripeCustomerReq(user);
    dto.setBalance(getRandomBalance());
    CustomerResDto customer = stripeServiceClient.createStripeCustomer(dto);
    return customer.getId();
  }

  private long getRandomBalance() {
    long leftLimit = 10_000_000L;
    long rightLimit = 100_000_000L;
    return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
  }

  public void updateById(String userId, UserReqDto dto) {
    User user = this.getById(userId, false);
    userMapper.updateModelFromDto(dto, user);
    updateStripeCustomer(user);
    repository.save(user);
  }

  private void updateStripeCustomer(User user) {
    CustomerReqDto dto = userMapper.model2StripeCustomerReq(user);
    stripeServiceClient.updateStripeCustomer(user.getStripeCustomer(), dto);
  }
}
