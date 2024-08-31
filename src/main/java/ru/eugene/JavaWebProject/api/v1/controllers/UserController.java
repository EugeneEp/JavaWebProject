package ru.eugene.JavaWebProject.api.v1.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.models.UserModel;
import ru.eugene.JavaWebProject.api.v1.models.errors.Errors;
import ru.eugene.JavaWebProject.api.v1.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements HandlerInterceptor {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserModel> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Page<UserModel> users = this.userService.getUsers(page, size);
        if (users.isEmpty()) throw new CustomErrorsModel(Errors.ERR_USERS_NOT_FOUND);
        return users;
    }

    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable("id") String id) {
        UserModel user = this.userService.getUserById(id);
        if (user == null) throw new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND);
        return user;
    }

    @PostMapping
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

    @PatchMapping("/{id}")
    public UserModel updateUser(@PathVariable("id") String id, @RequestBody Map<String, Object> payload) throws NoSuchAlgorithmException {
        return userService.updateUser(id,
                (String) payload.get("username"),
                (String) payload.get("password"));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") String id) {
        this.userService.deleteUserById(id);
    }
}
