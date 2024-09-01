package ru.eugene.JavaWebProject.api.v1.controllers;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.models.UserModel;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;
import ru.eugene.JavaWebProject.api.v1.models.requests.CreateUserRequest;
import ru.eugene.JavaWebProject.api.v1.models.requests.UpdateUserRequest;
import ru.eugene.JavaWebProject.api.v1.services.UserService;

import java.security.NoSuchAlgorithmException;

@SecurityRequirement(name = "Authorization")
@Tag(name = "Users", description = "Users management APIs")
@RestController
@RequestMapping("/api/v1/users")
public class UserController implements HandlerInterceptor {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users (paginated)")
    @GetMapping
    public Page<UserModel> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Page<UserModel> users = this.userService.getUsers(page, size);
        if (users.isEmpty()) throw new CustomErrorsModel(Errors.ERR_USERS_NOT_FOUND);
        return users;
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable("id") String id) {
        UserModel user = this.userService.getUserById(id);
        if (user == null) throw new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND);
        return user;
    }

    @Operation(summary = "Create user")
    @PostMapping
    public UserModel createUser(@RequestBody CreateUserRequest req) throws NoSuchAlgorithmException {
        if (StringUtils.isEmpty(req.username)) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);
        if (StringUtils.isEmpty(req.email)) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);
        if (StringUtils.isEmpty(req.password)) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);

        return userService.createUser(
                new UserModel(req.username, req.email, req.password)
        );
    }

    @Operation(summary = "Update user by id")
    @PatchMapping("/{id}")
    public UserModel updateUser(@PathVariable("id") String id, @RequestBody UpdateUserRequest req) throws NoSuchAlgorithmException {
        return userService.updateUser(id,
                req.username, req.password);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") String id) {
        this.userService.deleteUserById(id);
    }
}
