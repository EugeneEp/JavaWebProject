package ru.eugene.JavaWebProject.api.v1.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;
import ru.eugene.JavaWebProject.api.v1.services.JWTTokenService;

// Перехватчик, который отвечает за валидацию HTTP заголовков.
@Component
public class BearerTokenInterceptor implements HandlerInterceptor {
    private final JWTTokenService jwtTokenService;

    public BearerTokenInterceptor(JWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) throws Exception {
    }

    // Метод позволяет проверить заголовки на наличие JWT токена и провалидировать его.
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        if (token == null) throw new CustomErrorsModel(Errors.ERR_TOKEN_MISSING);
        if (!token.startsWith("Bearer")) throw new CustomErrorsModel(Errors.ERR_AUTH_METHOD_NOT_ALLOWED);
        String t = token.substring(7, token.length());
        if (t.isEmpty()) throw new CustomErrorsModel(Errors.ERR_TOKEN_MISSING);

        return jwtTokenService.verifyToken(t, "admin");
    }
}
