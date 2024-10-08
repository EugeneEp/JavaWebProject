package ru.eugene.JavaWebProject.api.v1.enums;

import org.springframework.http.HttpStatus;

// Набор константных кастомных ошибок
public enum Errors {
    ERR_USER_NOT_FOUND ("User not found", HttpStatus.NOT_FOUND),
    ERR_USERS_NOT_FOUND ("Users not found", HttpStatus.NOT_FOUND),
    ERR_USER_EXISTS ("User with this email already exists", HttpStatus.BAD_REQUEST),
    ERR_REQUIRED_FIELD ("Required field is empty", HttpStatus.BAD_REQUEST),
    ERR_AUTH ("Authentication data is incorrect", HttpStatus.UNAUTHORIZED),
    ERR_TOKEN_MISSING ("Token is missing", HttpStatus.FORBIDDEN),
    ERR_TOKEN_INVALID ("Token is invalid/expired", HttpStatus.FORBIDDEN),
    ERR_AUTH_METHOD_NOT_ALLOWED ("Auth method is not allowed",HttpStatus.METHOD_NOT_ALLOWED);

    private final String message;
    private final HttpStatus status;

    Errors(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
