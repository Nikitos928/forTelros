package com.example.demo.Photo;

import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.service.Photo.PhotoServiceImpl;
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
public class PhotoIntefrationTest {

    private final PhotoServiceImpl photoService;

    private final UserServiceImpl userService;

    @Test
    @SneakyThrows
    void getPhoto() {
        User user = User.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .birthday(LocalDate.of(2000, 5, 18))
                .email("email@email.ru")
                .phoneNumber("8999999999")
                .build();

        Photo photo = Photo.builder()
                .link("link")
                .description("description")
                .build();
        Long userId = userService.addUser(user).getId();

        Long photoId = photoService.addPhoto(photo, userId).getId();

        Photo photoFromDB= photoService.getPhoto(photoId);

        Assertions.assertEquals(photoFromDB.getLink(), "link");
        Assertions.assertEquals(photoFromDB.getDescription(), "description");

    }

}
