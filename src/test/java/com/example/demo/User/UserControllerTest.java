package com.example.demo.User;


import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.service.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    User user;

    @BeforeEach
    void createdUser () {
        user = User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .birthday(LocalDate.of(2000, 5, 18))
                .email("email@email.ru")
                .phoneNumber("8999999999")
                .build();
    }

    @Test
    @SneakyThrows
    void deleteUser() {
        mockMvc.perform(delete("/users/{id}", 1L)).andExpect(status().isOk());

        verify(userService).deleteUser(1L);
    }
    @Test
    @SneakyThrows
    void updateUser() {
        User newUser = User.builder()
                .firstName("newFirstName")
                .lastName("newLastName")
                .birthday(LocalDate.of(2010, 5, 18))
                .email("newemail@email.ru")
                .phoneNumber("81111111")
                .build();

        when(userService.updateUser(Mockito.any(), Mockito.any())).thenReturn(newUser);

        String result = mockMvc.perform(patch("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(newUser), result);

        verify(userService).updateUser(Mockito.any(), Mockito.any());

    }

    @Test
    @SneakyThrows
    void getUsers() {

        List<User> users = Arrays.asList(user, user);
        when(userService.getUsers()).thenReturn(users);

        String result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(users), result);

        verify(userService).getUsers();
    }




    @SneakyThrows
    @Test
    void getUser() {
        when(userService.getUser(Mockito.any())).thenReturn(user);
        String result = mockMvc.perform(get("/users/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(user), result);

        verify(userService).getUser(1L);


    }

    @SneakyThrows
    @Test
    void addUser() {

        when(userService.addUser(Mockito.any())).thenReturn(user);

        String result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(user), result);

        verify(userService).addUser(Mockito.any());
    }


    @Test
    @SneakyThrows
    void addUser_whenUserIsNotValidPhoneNumber_thenReturnedBadRequest() {
        user.setPhoneNumber("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).addUser(user);

    }
    @Test
    @SneakyThrows
    void addUser_whenUserIsNotValidEmail_thenReturnedBadRequest() {
        user.setEmail("notEmail");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).addUser(user);

    }

    @Test
    @SneakyThrows
    void addUser_whenUserIsNotValidBirthday_thenReturnedBadRequest() {
        user.setBirthday(LocalDate.of(3000, 1, 11));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).addUser(user);

    }

    @Test
    @SneakyThrows
    void addUser_whenUserIsNotValidLastName_thenReturnedBadRequest() {
        user.setLastName("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).addUser(user);

    }

    @Test
    @SneakyThrows
    void addUser_whenUserIsNotValidFirstName_thenReturnedBadRequest() {
        user.setFirstName("");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).addUser(user);

    }

}
