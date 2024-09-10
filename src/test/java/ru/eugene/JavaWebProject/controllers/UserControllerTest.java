package ru.eugene.JavaWebProject.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.eugene.JavaWebProject.api.v1.controllers.UserController;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.models.UserModel;
import ru.eugene.JavaWebProject.api.v1.models.requests.CreateUserRequest;
import ru.eugene.JavaWebProject.api.v1.models.requests.UpdateUserRequest;
import ru.eugene.JavaWebProject.api.v1.services.UserService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    private UserModel testUser = new UserModel(
            "test3232",
            "test3232@test3232.ru",
            "3232"
    ){{setId("3232");}};

    @Test
    @DisplayName("GET /api/v1/users возвращает список пользователей")
    void getUsers_ReturnsValidResponseEntity(){
        Page<UserModel> users = new PageImpl<UserModel>(List.of(
                new UserModel("test", "test@test.com", "test123"),
                new UserModel("test1", "test1@test.com", "test1123")
        ));

        doReturn(users).when(this.userService).getUsers(0,10);

        Page<UserModel> responseEntity = this.controller.getUsers(0,10);

        assertNotNull(responseEntity);
        assertEquals(users, responseEntity);
    }

    @Test
    @DisplayName("GET /api/v1/users возвращает ошибку, если get параметр size передан и меньше 0")
    void getUsers_ReturnsInvalidResponseEntity(){
        doThrow(new CustomErrorsModel(Errors.ERR_USERS_NOT_FOUND)).when(this.userService).getUsers(0,10);

        try {
            Page<UserModel> responseEntity = this.controller.getUsers(0,10);
        } catch (CustomErrorsModel e) {
            assertEquals(Errors.ERR_USERS_NOT_FOUND.getMessage(), e.getMessage());
        }
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} возвращает пользователя по ID")
    void getUserById_ReturnsValidResponseEntity(){
        doReturn(testUser).when(this.userService).getUserById(testUser.getId());

        UserModel responseEntity = this.controller.getUserById(testUser.getId());

        assertNotNull(responseEntity);
        assertEquals(testUser, responseEntity);
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} возвращает ошибку если пользователь не найден")
    void getUserById_ReturnsInvalidResponseEntity(){
        doThrow(new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND)).when(this.userService).getUserById("1");

        try {
            UserModel responseEntity = this.controller.getUserById("1");
        } catch (CustomErrorsModel e) {
            assertEquals(Errors.ERR_USER_NOT_FOUND.getMessage(), e.getMessage());
        }

    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} возвращает ошибку если пользователь не найден")
    void deleteUserById_ReturnsInvalidResponseEntity(){
        doThrow(new CustomErrorsModel(Errors.ERR_USER_NOT_FOUND)).when(this.userService).deleteUserById("1");

        try {
            this.controller.deleteUserById("1");
        } catch (CustomErrorsModel e) {
            assertEquals(Errors.ERR_USER_NOT_FOUND.getMessage(), e.getMessage());
        }
    }
}