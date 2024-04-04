package com.ibanking.userservice.user;

import com.ibanking.userservice.client.CustomerReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

  UserResDto model2Dto(User user);

  @Mapping(target = "name", source = "fullName")
  CustomerReqDto model2StripeCustomerReq(User user);

  void updateModelFromDto(UserReqDto dto, @MappingTarget User user);

  @Mapping(
      target = "createdAt",
      expression = "java( user.getCreatedAt().toInstant().getEpochSecond() )")
  @Mapping(
      target = "updatedAt",
      expression = "java( user.getUpdatedAt().toInstant().getEpochSecond() )")
  User auth02Model(com.auth0.json.mgmt.users.User user);
}
