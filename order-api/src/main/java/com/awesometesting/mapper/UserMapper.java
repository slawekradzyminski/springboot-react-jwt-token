package com.awesometesting.mapper;

import com.awesometesting.dto.user.UserDto;
import com.awesometesting.model.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
}