package ru.eugene.JavaWebProject.models.errors;

import org.springframework.http.HttpStatus;

public enum Errors {
    ERR_USER_NOT_FOUND ("User not found", HttpStatus.NOT_FOUND),
    ERR_USERS_NOT_FOUND ("Users not found", HttpStatus.NOT_FOUND),
    ERR_REQUIRED_FIELD ("Required field is empty", HttpStatus.BAD_REQUEST);

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
