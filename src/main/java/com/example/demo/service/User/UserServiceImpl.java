package com.example.demo.service.User;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.storage.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User addUser(User user) {
        log.info("Вызван метод: addUser " + user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        log.info("Вызван метод: getUsers ");
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) throws NotFoundException {
        log.info("Вызван метод: getUser " + userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id= " + userId + " не найден!"));
    }

    @Transactional
    @Override
    public User updateUser(Long userId, User user) throws NotFoundException {
        log.info("Вызван метод: updateUser userId = " + userId + "User = " + user);

        User updateUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id= " + userId + " не найден!"));

        if (user.getFirstName() != null) {
            updateUser.setFirstName(user.getFirstName());
        }
        if (user.getSurname() != null) {
            updateUser.setSurname(user.getSurname());
        }
        if (user.getLastName() != null) {
            updateUser.setLastName(user.getLastName());
        }
        if (user.getBirthday() != null) {
            updateUser.setBirthday(user.getBirthday());
        }
        if (user.getEmail() != null) {
            updateUser.setEmail(user.getEmail());
        }
        if (user.getPhoneNumber() != null) {
            updateUser.setPhoneNumber(user.getPhoneNumber());
        }

        return userRepository.save(updateUser);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Вызван метод: deleteUser " + userId);
        userRepository.deleteById(userId);
    }
}
