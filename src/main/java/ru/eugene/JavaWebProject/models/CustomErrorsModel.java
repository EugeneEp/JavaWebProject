package ru.eugene.JavaWebProject.models;

import org.springframework.http.HttpStatus;
import ru.eugene.JavaWebProject.models.errors.Errors;

import java.util.HashMap;
import java.util.Map;

public class CustomErrorsModel extends RuntimeException {
    private HttpStatus status;
    private Map<String, String> message;

    public CustomErrorsModel(Errors error) {
        Map<String,String> message = new HashMap<>();
        message.put("message", error.getMessage());
        message.put("status", "error");
        this.setMessage(message);
        this.setStatus(error.getStatus());
    }

    public CustomErrorsModel(String _message, HttpStatus status) {
        Map<String,String> message = new HashMap<>();
        message.put("message", _message);
        message.put("status", "error");
        this.setMessage(message);
        this.setStatus(status);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public Map<String, String> getMappedMessage() {
        return message;
    }
}
