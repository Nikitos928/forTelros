package com.example.demo.User;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.User.UserServiceImpl;
import com.example.demo.storage.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    User user;

    @BeforeEach
    void createdUser() {
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
    void updateUser() {
        User newUser = User.builder()
                .firstName("newFirstName")
                .lastName("newLastName")
                .birthday(LocalDate.of(2010, 5, 18))
                .email("newemail@email.ru")
                .phoneNumber("81111111")
                .build();

        when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any())).thenReturn(newUser);

        Assertions.assertEquals(newUser, userService.updateUser(1L, newUser));

        verify(userRepository).save(userArgumentCaptor.capture());
        User userCaptor = userArgumentCaptor.getValue();


        Assertions.assertEquals(userCaptor.getFirstName(), newUser.getFirstName());
        Assertions.assertEquals(userCaptor.getLastName(), newUser.getLastName());
        Assertions.assertEquals(userCaptor.getBirthday(), newUser.getBirthday());
        Assertions.assertEquals(userCaptor.getEmail(), newUser.getEmail());
        Assertions.assertEquals(userCaptor.getPhoneNumber(), newUser.getPhoneNumber());

    }

    @Test
    @SneakyThrows
    void updateUser_whenUserNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.updateUser(1L, user));
    }

    @Test
    @SneakyThrows
    void getUser_whenUserNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.getUser(Mockito.any()));
    }

    @Test
    @SneakyThrows
    void getUsers() {
        List<User> users = Arrays.asList(user, user);
        when(userRepository.findAll()).thenReturn(users);

        Assertions.assertEquals(userService.getUsers(), users);

    }

    @Test
    @SneakyThrows
    void addUser() {
        when(userRepository.save(Mockito.any())).thenReturn(user);

        Assertions.assertEquals(userService.addUser(user), user);

    }
}
