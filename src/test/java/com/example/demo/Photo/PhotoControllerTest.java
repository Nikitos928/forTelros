package com.example.demo.Photo;

import com.example.demo.controller.PhotoController;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.service.Photo.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PhotoController.class)
public class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PhotoService photoService;

    Photo photo;

    @BeforeEach
    void createdUser() {
        photo = Photo.builder().link("link").author(User.builder().build()).build();
    }


    @SneakyThrows
    @Test
    void deletePhoto() {
        when(photoService.updatePhoto(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(photo);

        String result = mockMvc.perform(delete("/photos/1")
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(photo)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        verify(photoService).deletePhoto(Mockito.any(), Mockito.any());
    }

    @SneakyThrows
    @Test
    void updatePhoto() {
        when(photoService.updatePhoto(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(photo);

        String result = mockMvc.perform(patch("/photos/1")
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(photo)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(photo), result);

        verify(photoService).updatePhoto(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @SneakyThrows
    @Test
    void getPhoto() {
        when(photoService.getPhoto(Mockito.any())).thenReturn(photo);

        String result = mockMvc.perform(get("/photos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(photo), result);

        verify(photoService).getPhoto(Mockito.any());
    }

    @SneakyThrows
    @Test
    void addPhoto() {
        when(photoService.addPhoto(Mockito.any(), Mockito.any())).thenReturn(photo);

        String result = mockMvc.perform(post("/photos")
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(photo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(photo), result);

        verify(photoService).addPhoto(Mockito.any(), Mockito.any());
    }

}
