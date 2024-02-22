package com.todoapp.user.mapper;

import com.todoapp.user.dto.UserDto;
import com.todoapp.user.dto.UserResponseDto;
import com.todoapp.user.entity.User;

public class UserMapper {
    public static User from(UserDto userDto, User user) {
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserResponseDto from(User user, UserResponseDto userResponseDto){
        userResponseDto.setName(user.getName());
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }


}
