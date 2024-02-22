package com.todoapp.user.service;

import com.todoapp.user.dto.UserDto;
import com.todoapp.user.dto.UserResponseDto;
import com.todoapp.user.entity.User;
import com.todoapp.user.exception.UserAlreadyExistsException;
import com.todoapp.user.exception.UserNotFoundException;
import com.todoapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.todoapp.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.todoapp.user.constants.ApplicationConstants.USER_ALREADY_EXISTS_WITH_EMAIL;
import static com.todoapp.user.constants.ApplicationConstants.USER_NOT_FOUND;

@Service
public class UserService  {


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public UserResponseDto createUser(UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS_WITH_EMAIL +  userDto.getEmail());
        }
        User  user  = UserMapper.from(userDto, new User());
        userRepository.save(user);
        return UserMapper.from(user, new UserResponseDto());
    }


    public List<UserResponseDto> findAllUser() {

        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponse = users.stream()
                .map(it -> {
                    return UserMapper.from(it, new UserResponseDto());
                }).collect(Collectors.toList());

        return userResponse;
    }


    public UserResponseDto updateUser(UserDto userDto, String email) {

        if( !userRepository.findByEmail(email).isPresent()){
            throw new UserNotFoundException(USER_NOT_FOUND +  email);
        }

        User user = userRepository.findByEmail(email).get();

        //do not update email which is unique
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return UserMapper.from(user, new UserResponseDto());
    }


    public boolean deleteUserByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if( !user.isPresent()){
            throw new UserNotFoundException(USER_NOT_FOUND +  email);
        }
        userRepository.delete(user.get());
        return true;
    }

    private static void resetEmail(UserDto userDto, User user) {
        user.setEmail(userDto.getEmail());
    }


}
