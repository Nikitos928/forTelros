package com.example.demo.controller;


import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Photo;
import com.example.demo.service.Photo.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Photo addPhoto(@Valid @RequestBody Photo photo, @RequestHeader("X-Sharer-User-Id") Long userId) throws NotFoundException {
        log.info("запрос: Post /photos");
        return photoService.addPhoto(photo, userId);
    }


    @GetMapping("/{id}")
    public Photo getPhoto(@PathVariable(value = "id") Long photoId) throws NotFoundException, BadRequestException {
        log.info("запрос: Get /photos/{id}" + photoId);
        return photoService.getPhoto(photoId);
    }

    @PatchMapping("/{id}")
    public Photo updateUser(@PathVariable(value = "id") Long photoId,
                            @Valid @RequestBody Photo photo,
                            @RequestHeader("X-Sharer-User-Id") Long userId) throws NotFoundException {
        log.info("запрос: Patch /photos/{id}");
        return photoService.updatePhoto(photoId, userId, photo);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable(value = "id") Long photoId,
                            @RequestHeader("X-Sharer-User-Id") Long userId) throws NotFoundException {
        log.info("запрос: Delete /photos/{id}");
        photoService.deletePhoto(photoId, userId);
    }

}
