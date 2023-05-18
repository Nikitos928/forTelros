package com.example.demo.service.User;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    List<User> getUsers();

    User getUser(Long userId) throws NotFoundException;

    User updateUser(Long userId, User user) throws NotFoundException;

    void deleteUser(Long userId);

}
