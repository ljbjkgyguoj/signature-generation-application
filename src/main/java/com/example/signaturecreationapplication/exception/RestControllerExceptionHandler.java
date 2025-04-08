package com.example.signaturecreationapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Перехватчик исключений для контроллеров.
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(SignatureGenerationException.class)
    public ResponseEntity<String> handleSignatureGenerationException(SignatureGenerationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка при генерации подписи: " + e.getMessage());
    }
}
