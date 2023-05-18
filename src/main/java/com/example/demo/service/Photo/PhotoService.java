package com.example.demo.service.Photo;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Photo;
import com.example.demo.model.User;

import java.util.List;

public interface PhotoService {

    Photo addPhoto(Photo photo, Long userId) throws NotFoundException;


    Photo getPhoto (Long photoId) throws NotFoundException, BadRequestException;

    Photo updatePhoto(Long photoId, Long userId, Photo photo) throws NotFoundException;

    void deletePhoto (Long photoId, Long userId) throws NotFoundException;



}
