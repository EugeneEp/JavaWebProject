package ru.eugene.JavaWebProject.models.errors;

import org.springframework.http.HttpStatus;

public enum Errors {
    ERR_USER_NOT_FOUND ("User not found", HttpStatus.NOT_FOUND),
    ERR_USERS_NOT_FOUND ("Users not found", HttpStatus.NOT_FOUND),
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
