package com.example.demo.service.Photo;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.storage.PhotoRepository;
import com.example.demo.storage.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Slf4j
@Service
@Transactional(readOnly = true)
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final UserRepository userRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Photo addPhoto(Photo photo, Long userId) throws NotFoundException {
        log.info("Вызван метод: addPhoto photo = " + photo + "userId = " + userId);
        User user = checkUser(userId);
        photo.setAuthor(user);
        return photoRepository.save(photo);
    }


    @Override
    public Photo getPhoto(Long photoId) throws NotFoundException {
        log.info("Вызван метод: getPhoto photoId = " + photoId);
        return checkPhoto(photoId);
    }

    @Override
    public Photo updatePhoto(Long photoId, Long userId, Photo photo) throws NotFoundException {
        log.info("Вызван метод: updatePhoto photoId = " + photoId);
        Photo updatePhoto = checkPhoto(photoId);
        checkUserBoolean(userId);

        if (photo.getLink() != null) {
            updatePhoto.setLink(photo.getLink());
        }
        if (photo.getDescription() != null) {
            updatePhoto.setDescription(photo.getDescription());
        }
        return photoRepository.save(updatePhoto);
    }


    @Override
    public void deletePhoto(Long photoId, Long userId) throws NotFoundException {
        log.info("Вызван метод: updatePhoto photoId = " + photoId);
        Photo photo = checkPhoto(photoId);
        if (Objects.equals(photo.getAuthor().getId(), userId)) {
            throw new NotFoundException("Только пользователь может удалить фото");
        }
        photoRepository.deleteById(photoId);
    }

    private Photo checkPhoto(Long photoId) throws NotFoundException {
        log.info("Вызван метод: checkPhoto photoId = " + photoId);
        return photoRepository.findById(photoId).orElseThrow(
                () -> new NotFoundException("Фото c id= " + photoId + " не найдено"));
    }

    private User checkUser(Long userId) throws NotFoundException {
        log.info("Вызван метод: heckUser userId = " + userId);
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
    }

    private void checkUserBoolean(Long userId) throws NotFoundException {
        log.info("Вызван метод: checkUserBoolean userId = " + userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
    }
}
