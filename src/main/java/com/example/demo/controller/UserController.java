package com.example.demo.controller;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        log.info("запрос: Post /users");
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("запрос: Get /users");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") Long userId) throws NotFoundException {
        log.info("запрос: Get /users/{id}");
        return userService.getUser(userId);
    }


    @PatchMapping("/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User user) throws NotFoundException {
        log.info("запрос: Patch /users/{id}");
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        log.info("запрос: Delete /users/{id}");
        userService.deleteUser(userId);
    }


}
