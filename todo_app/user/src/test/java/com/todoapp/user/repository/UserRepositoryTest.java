package com.todoapp.user.repository;

import com.todoapp.user.entity.User;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User testUser;

    @BeforeEach
    public void init(){
        testUser = User.builder()
                .name("test")
                .password("12234")
                .email("test@email.com")
                .build();
    }

    @AfterEach
    public void cleanup(){
        testUser = null;
    }

    @Test
    public void testUserShouldLoadFromDatabaseSuccess(){
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals(3, users.size());
        String targetEmail = "@";
        assertTrue(users.stream().anyMatch(user -> user.getEmail().contains(targetEmail)),
                "List does not contain a user with the email: " + targetEmail);

    }

    //create a user with valid data
    @Test
    public void testUserCreateUserSuccess(){
        userRepository.save(testUser);
        assertNotNull(testUser.getId());
    }

    @Test
    public void testUserFindByEmailSuccess(){
        userRepository.save(testUser);
        User dbUser =   userRepository.findByEmail("test@email.com").get();
        assertNotNull(dbUser);
        assertEquals("test@email.com", dbUser.getEmail());

    }

    @Test
    public void testUserFindByEmailInvalidEmailReturnNull(){
       Optional<User> dbUser =   userRepository.findByEmail("invalid@email.com");
        assertFalse(dbUser.isPresent());
    }



    @Test
    public void testUserUpdateSuccess(){
        userRepository.save(testUser);
        User dbUser =   userRepository.findByEmail("test@email.com").get();

        dbUser.setEmail("newEmail@email.com");
        userRepository.save(testUser);
        assertNotNull(dbUser);
        assertEquals("newEmail@email.com", dbUser.getEmail());

    }

    @Test
    public void testUserDeleteSuccess(){
        userRepository.save(testUser);

        userRepository.delete(testUser);
        Optional<User> dbUser =   userRepository.findByEmail("test@email.com");
        assertFalse(dbUser.isPresent());



    }
}
