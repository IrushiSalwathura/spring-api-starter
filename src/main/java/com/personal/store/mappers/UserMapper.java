package com.personal.store.mappers;

import com.personal.store.dtos.RegisterUserRequest;
import com.personal.store.dtos.UpdateUserRequest;
import com.personal.store.dtos.UserDto;
import com.personal.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request); //Mapping for the new dto(RegisterUserRequest) and user entity
    void update(UpdateUserRequest request, @MappingTarget User user);

}
