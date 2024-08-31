package ru.eugene.JavaWebProject.api.v1.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.eugene.JavaWebProject.api.v1.models.CustomErrorsModel;


@ControllerAdvice
public class CustomErrorsController extends ResponseEntityExceptionHandler  {
    @ExceptionHandler(value = { CustomErrorsModel.class })
    public ResponseEntity<Object> handleApiRequestException(CustomErrorsModel e) {
        return ResponseEntity.status(e.getStatus())
                .contentType(MediaType.APPLICATION_JSON).body(e.getMappedMessage());
    }
}
