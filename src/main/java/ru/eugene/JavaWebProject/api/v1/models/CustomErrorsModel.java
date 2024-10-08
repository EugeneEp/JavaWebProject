package ru.eugene.JavaWebProject.api.v1.models;

import org.springframework.http.HttpStatus;
import ru.eugene.JavaWebProject.api.v1.enums.Errors;

import java.util.HashMap;
import java.util.Map;

// Класс, в котором переделана базовая логика формирования ответа исключений
public class CustomErrorsModel extends RuntimeException {
    private HttpStatus status;
    private Map<String, String> message;
    private String detailedMessage;

    public CustomErrorsModel(Errors error) {
        Map<String,String> message = new HashMap<>();
        message.put("message", error.getMessage());
        message.put("status", "error");
        this.setMessage(message);
        this.setStatus(error.getStatus());
        this.setDetailedMessage(error.getMessage());
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

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }

    @Override
    public String getMessage() {
        return this.detailedMessage;
    }

    public Map<String, String> getMappedMessage() {
        return message;
    }
}
