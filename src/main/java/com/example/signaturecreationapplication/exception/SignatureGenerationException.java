package com.example.signaturecreationapplication.exception;

/**
 * Исключение при ошибке формирования подписи.
 */
public class SignatureGenerationException extends RuntimeException {
    public SignatureGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}