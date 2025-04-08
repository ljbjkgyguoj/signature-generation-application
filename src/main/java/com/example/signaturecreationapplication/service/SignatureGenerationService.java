package com.example.signaturecreationapplication.service;

import com.example.signaturecreationapplication.dto.SignatureGenerationResponseDto;

import java.util.Map;

/**
 * Интерфейс для генерации подписи.
 */
public interface SignatureGenerationService {

    /**
     * Метод для генерации HMAC SHA256 подписи из входных параметров.
     *
     * @param parameters параметры запроса
     * @return {@link SignatureGenerationResponseDto} подпись
     */
    SignatureGenerationResponseDto generateSignature(Map<String, String> parameters);
}
