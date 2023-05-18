package com.example.demo.User;


import com.example.demo.model.User;
import com.example.demo.service.User.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {

    private final UserServiceImpl userService;

    @Test
    @SneakyThrows
    public void getUserTest() {
       User user1_1 = User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .birthday(LocalDate.of(2000, 5, 18))
                .email("email@email.ru")
                .phoneNumber("8999999999")
                .build();

        User user1_2 = User.builder()
                .firstName("newfirstName")
                .lastName("newlastName")
                .birthday(LocalDate.of(2010, 5, 18))
                .email("newemail@email.ru")
                .phoneNumber("81111111")
                .build();

        Long userId1 = userService.addUser(user1_1).getId();
        Long userId2 = userService.addUser(user1_2).getId();

        User user1 = userService.getUser(userId1);
        User user2 = userService.getUser(userId2);

        Assertions.assertEquals(user1_1.getFirstName(), user1.getFirstName());
        Assertions.assertEquals(user1_1.getLastName(), user1.getLastName());
        Assertions.assertEquals(user1_1.getBirthday(), user1.getBirthday());
        Assertions.assertEquals(user1_1.getPhoneNumber(), user1.getPhoneNumber());

        Assertions.assertEquals(user1_2.getFirstName(), user2.getFirstName());
        Assertions.assertEquals(user1_2.getLastName(), user2.getLastName());
        Assertions.assertEquals(user1_2.getBirthday(), user2.getBirthday());
        Assertions.assertEquals(user1_2.getPhoneNumber(), user2.getPhoneNumber());


    }
}
