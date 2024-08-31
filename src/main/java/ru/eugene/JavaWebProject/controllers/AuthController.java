package ru.eugene.JavaWebProject.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.eugene.JavaWebProject.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.models.errors.Errors;
import ru.eugene.JavaWebProject.services.JWTTokenService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final JWTTokenService jwtTokenService;

    public AuthController(JWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/auth")
    public Map<String,String> getApiKey(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");
        String password = (String) payload.get("password");
        if (username == null | username.isEmpty()) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);
        if (password == null | password.isEmpty()) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);

        if (!username.equals("admin") && !password.equals("admin")) throw new CustomErrorsModel(Errors.ERR_AUTH);

        Map<String,String> res = new HashMap<>();
        res.put("api_key", this.jwtTokenService.tokenCreate(username));

        return res;
    }

}
