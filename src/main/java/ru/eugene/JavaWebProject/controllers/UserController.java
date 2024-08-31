package ru.eugene.JavaWebProject.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.PrePersist;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.eugene.JavaWebProject.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.models.UserModel;
import ru.eugene.JavaWebProject.models.errors.Errors;
import ru.eugene.JavaWebProject.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserController implements HandlerInterceptor {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Page<UserModel> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Page<UserModel> users = this.userService.getUsers(page, size);
        if (users.isEmpty()) throw new CustomErrorsModel(Errors.ERR_USERS_NOT_FOUND);
        return users;
    }

    @GetMapping("/users/{id}")
    public UserModel getUserById(@PathVariable("id") String id) {
        UserModel user = this.userService.getUserById(id);
        if (user == null) throw new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND);
        return user;
    }

    @PostMapping("/users")
    public UserModel createUser(@RequestBody Map<String, Object> payload) throws NoSuchAlgorithmException {
        String username = (String) payload.get("username");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        if (username == null | username.isEmpty()) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);
        if (email == null | email.isEmpty()) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);
        if (password == null | password.isEmpty()) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);

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
