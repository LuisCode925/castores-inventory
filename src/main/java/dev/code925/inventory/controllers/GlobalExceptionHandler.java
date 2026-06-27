package dev.code925.inventory.controllers;

import java.time.OffsetDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.code925.inventory.exceptions.ProductNotFoundException;
import dev.code925.inventory.exceptions.ProductWithoutEnoughtStockException;
import dev.code925.inventory.exceptions.TokenNotFoundException;
import dev.code925.inventory.models.dto.output.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        var errors = new HashMap<String, String>();
        errors.put("message", "Se ha producido un error desconocido. Contacte al administrador.");
        errors.put("datetime", OffsetDateTime.now().toString());

        log.error("Error: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValid(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        errors.put("datetime", OffsetDateTime.now().toString());
        log.warn("Validations Errors: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValid(ProductNotFoundException exception) {
        var errors = new HashMap<String, String>();
        errors.put("message", "El producto que usted buscó no existe o no esta de momento disponible.");
        errors.put("datetime", OffsetDateTime.now().toString());
        log.warn("Validations Errors: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(ProductWithoutEnoughtStockException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValid(ProductWithoutEnoughtStockException exception) {
        var errors = new HashMap<String, String>();
        errors.put("message", "El producto no cuenta con las suficientes existencias quew solicita.");
        errors.put("datetime", OffsetDateTime.now().toString());
        log.warn("Validations Errors: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValid(TokenNotFoundException exception) {
        var errors = new HashMap<String, String>();
        errors.put("message", "Token invalido.");
        errors.put("datetime", OffsetDateTime.now().toString());
        log.warn("Validations Errors: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

}
