package ru.eugene.JavaWebProject.api.v1.controllers;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;
import ru.eugene.JavaWebProject.api.v1.models.requests.GetAPIKeyRequest;
import ru.eugene.JavaWebProject.api.v1.services.JWTTokenService;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth", description = "Get auth token API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JWTTokenService jwtTokenService;

    public AuthController(JWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    public Map<String,String> getAPIKey(@RequestBody GetAPIKeyRequest req) {
        if (StringUtils.isEmpty(req.username)) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);
        if (StringUtils.isEmpty(req.password)) throw new CustomErrorsModel(Errors.ERR_REQUIRED_FIELD);

        if (!req.username.equals("admin") && !req.password.equals("admin")) throw new CustomErrorsModel(Errors.ERR_AUTH);

        Map<String,String> res = new HashMap<>();
        res.put("api_key", this.jwtTokenService.tokenCreate(req.username));

        return res;
    }

}
