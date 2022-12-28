package com.ivanfranchin.orderapi.mapper;

import com.ivanfranchin.orderapi.dto.user.UserDto;
import com.ivanfranchin.orderapi.model.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
}