package ru.eugene.JavaWebProject.api.v1.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenService {

    @Value("${jwttokenservice.api-key}")
    private String ApiKey;
    @Value("${jwttokenservice.expires-at-hours}")
    private Integer ExpiresAtHours;

    // Метод для создания JWT токена
    public String tokenCreate(String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(this.ApiKey);
        Map<String,String> res = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, this.ExpiresAtHours);

        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(cal.getTime())
                .sign(algorithm);
    }

    // Метод для сверки JWT токена на корректность и просроченность
    public boolean verifyToken(String token, String issuer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.ApiKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            throw new CustomErrorsModel(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
