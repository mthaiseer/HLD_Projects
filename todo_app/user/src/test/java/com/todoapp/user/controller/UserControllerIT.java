package com.todoapp.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.user.dto.UserDto;
import com.todoapp.user.repository.UserRepository;
import com.todoapp.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

   // @Test
    public void testCreateUserSuccess() throws Exception {

        UserDto testUser = UserDto.builder()
                .name("test_user")
                .email("test@email.com")
                .password("Abbs#12")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testUser)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test_user"));




    }

}
