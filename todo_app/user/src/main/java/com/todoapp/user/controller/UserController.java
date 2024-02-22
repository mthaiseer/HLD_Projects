package com.todoapp.user.controller;

import com.todoapp.user.dto.UserDto;
import com.todoapp.user.dto.UserResponseDto;
import com.todoapp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid  @RequestBody UserDto userDto){

        UserResponseDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

         List<UserResponseDto> users = userService.findAllUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(users);

    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@Valid  @RequestBody UserDto userDto
            ,@RequestParam String email){
        UserResponseDto user = userService.updateUser(userDto, email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam String email){
         userService.deleteUserByEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete operation successful");
    }


}
