package ru.eugene.JavaWebProject.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.eugene.JavaWebProject.api.v1.controllers.AuthController;
import ru.eugene.JavaWebProject.api.v1.controllers.UserController;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.models.UserModel;
import ru.eugene.JavaWebProject.api.v1.models.requests.GetAPIKeyRequest;
import ru.eugene.JavaWebProject.api.v1.services.JWTTokenService;
import ru.eugene.JavaWebProject.api.v1.services.UserService;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    JWTTokenService jwtTokenService;

    @InjectMocks
    AuthController controller;

    @Test
    @DisplayName("POST /api/v1/auth возвращает JWT токен для доступа к методам по пути /users/")
    void getAPIKey_returnsValidResponseEntity(){
        GetAPIKeyRequest req = new GetAPIKeyRequest();
        req.username = "admin";
        req.password = "admin";

        String token = "123";

        doReturn(token).when(this.jwtTokenService).tokenCreate(req.username);

        Map<String, String> responseEntity = controller.getAPIKey(req);

        assertNotNull(responseEntity);
        assertEquals(token, responseEntity.get("api_key"));
    }
}