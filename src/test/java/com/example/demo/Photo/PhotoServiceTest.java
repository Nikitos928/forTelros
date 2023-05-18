package com.example.demo.Photo;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.service.Photo.PhotoServiceImpl;
import com.example.demo.storage.PhotoRepository;
import com.example.demo.storage.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PhotoServiceImpl photoService;

    @Captor
    private ArgumentCaptor<Photo> photoArgumentCaptor;

    User user;

    Photo photo;

    @BeforeEach
    void createUserAndPhoto() {
        user = User.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .birthday(LocalDate.of(2000, 5, 18))
                .email("email@email.ru")
                .phoneNumber("8999999999")
                .build();

        photo = Photo.builder()
                .author(user)
                .link("link")
                .description("description")
                .build();
    }

    @Test
    @SneakyThrows
    void updatePhoto() {
        Photo newPhoto = Photo.builder()
                .author(user)
                .link("newLink")
                .description("newDescription")
                .build();

        when(photoRepository.findById(Mockito.any())).thenReturn(Optional.of(photo));
        when(userRepository.existsById(Mockito.any())).thenReturn(true);
        when(photoRepository.save(Mockito.any())).thenReturn(photo);
        Assertions.assertEquals(photo, photoService.updatePhoto(1L, 1L, newPhoto));

        verify(photoRepository).save(photoArgumentCaptor.capture());
        Photo userCaptor = photoArgumentCaptor.getValue();
        Assertions.assertEquals(userCaptor.getDescription(), newPhoto.getDescription());
        Assertions.assertEquals(userCaptor.getLink(), newPhoto.getLink());

    }

    @Test
    @SneakyThrows
    void updatePhoto_whenPhotoNotFound() {

        when(photoRepository.findById(Mockito.any())).thenReturn(Optional.of(photo));
        when(userRepository.existsById(Mockito.any())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> photoService.updatePhoto(1L, 1L, photo));
    }
    @Test
    @SneakyThrows
    void getPhoto() {
        when(photoRepository.findById(Mockito.any())).thenReturn(Optional.of(photo));
        Assertions.assertEquals(photo, photoService.getPhoto(1L));
    }

    @Test
    @SneakyThrows
    void addPhoto() {
        when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(photoRepository.save(Mockito.any())).thenReturn(photo);
        Assertions.assertEquals(photo, photoService.addPhoto(photo, 1L));
    }

    @Test
    @SneakyThrows
    void addPhoto_whenPhotoNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> photoService.addPhoto(photo, 1L));
    }

}
