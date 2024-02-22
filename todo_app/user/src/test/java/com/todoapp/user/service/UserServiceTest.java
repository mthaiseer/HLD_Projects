package com.todoapp.user.service;

import com.todoapp.user.dto.UserDto;
import com.todoapp.user.dto.UserResponseDto;
import com.todoapp.user.entity.User;
import com.todoapp.user.exception.UserAlreadyExistsException;
import com.todoapp.user.exception.UserNotFoundException;
import com.todoapp.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final String TEST_MAIL = "test@email.com";
    public static final String UPDATED_NAME = "MyNewName";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User testUser;
    UserDto userDto = null;

    UserDto updateUserDto = null;

    @BeforeEach
    public void init(){
        testUser = User.builder()
                .id(null)
                .name("test")
                .password("12234")
                .email(TEST_MAIL)
                .build();

        userDto = UserDto.builder()
                .name("test")
                .password("12234")
                .email(TEST_MAIL)
                .build();
        updateUserDto =  UserDto.builder()
                .name(UPDATED_NAME)
                .password("12234")
                .email(TEST_MAIL)
                .build();

    }

    @AfterEach
    public void clean(){
        userDto = null;
        testUser = null;
        updateUserDto = null;
    }

    @Test
    public void testCreateUserSuccess(){
        //setup
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        UserResponseDto userDb =  userService.createUser(userDto);
        assertNotNull(userDb);
        assertEquals(TEST_MAIL, userDb.getEmail());
    }

    @Test
    public void testUserShouldNotAddedIfEmailAlreadyExists(){

        when(userRepository.findByEmail(TEST_MAIL)).thenReturn(Optional.of(testUser));
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
    }

    //Get all users
    @Test
    public void testUserShouldReturnEmptyListIfNoUserAvailable(){
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponseDto> usersDto = userService.findAllUser();
        assertNotNull(usersDto);
        assertEquals(0, usersDto.size());
    }

    @Test
    public void testUserGetAllUsersShouldReturnList(){
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<UserResponseDto> response  =  userService.findAllUser();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(TEST_MAIL, response.get(0).getEmail());

    }

    // User should update successfully
    @Test
    public void testUpdateUserShouldBeSuccessful(){
        when(userRepository.findByEmail(TEST_MAIL)).thenReturn(Optional.of(testUser));

        UserResponseDto responseDto =  userService.updateUser(updateUserDto, TEST_MAIL);
        updateUserDto.setEmail("shouldnotupdate@email.com");
        assertNotNull(responseDto);
        assertEquals(UPDATED_NAME, responseDto.getName());
        assertEquals(TEST_MAIL, responseDto.getEmail());


    }
    // UserAlreadyExistsException should thrown if update user not present
    @Test
    public void testUpdateUserShouldThrowExceptionIfUserNotFound(){

        when(userRepository.findByEmail(TEST_MAIL)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(updateUserDto, TEST_MAIL));

    }

    //user should delete successfully
    @Test
    public void deleteUserByEmailSuccessfully(){
        when(userRepository.findByEmail(TEST_MAIL)).thenReturn(Optional.of(testUser));
        boolean isDeleted  = userService.deleteUserByEmail(TEST_MAIL);
        assertEquals(true, isDeleted);


    }
    // UserAlreadyExistsException should thrown if delete user not present
    @Test
    public void deleteUserShouldThrowExceptionIfUserNotFound(){
        when(userRepository.findByEmail(TEST_MAIL)).thenReturn(Optional.empty());
        assertThrows(UserAlreadyExistsException.class, () -> userService.deleteUserByEmail(TEST_MAIL));
    }
}
