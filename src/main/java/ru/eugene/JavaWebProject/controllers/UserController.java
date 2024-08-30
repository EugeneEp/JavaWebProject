package ru.eugene.JavaWebProject.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.eugene.JavaWebProject.models.UserModel;
import ru.eugene.JavaWebProject.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Page<UserModel> getUsers (@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return this.userService.getUsers(page, size);
    }

    @GetMapping("/users/{id}")
    public UserModel getUserById(@PathVariable("id") String id) {
        return this.userService.getUserById(id);
    }

    @PostMapping("/users")
    public UserModel createUser(@RequestBody Map<String, Object> payload) throws NoSuchAlgorithmException {
        String username = (String) payload.get("username");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        if (username == null | username.isEmpty()) return null;
        if (email == null | email.isEmpty()) return null;
        if (password == null | password.isEmpty()) return null;

        UserModel user = new UserModel(username, email, password);

        return userService.createUser(user);
    }

    @PatchMapping("/users/{id}")
    public UserModel updateUser(@PathVariable("id") String id, @RequestBody Map<String, Object> payload) throws NoSuchAlgorithmException {
        return userService.updateUser(id,
                (String) payload.get("username"),
                (String) payload.get("password"));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") String id) {
        this.userService.deleteUserById(id);
    }
}
