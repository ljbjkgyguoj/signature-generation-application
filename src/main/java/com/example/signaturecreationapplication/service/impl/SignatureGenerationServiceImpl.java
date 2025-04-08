package com.example.signaturecreationapplication.service.impl;

import com.example.signaturecreationapplication.dto.SignatureDto;
import com.example.signaturecreationapplication.dto.SignatureGenerationResponseDto;
import com.example.signaturecreationapplication.exception.SignatureGenerationException;
import com.example.signaturecreationapplication.service.SignatureGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реализация {@link SignatureGenerationService}.
 */
@Service
@Slf4j
public class SignatureGenerationServiceImpl implements SignatureGenerationService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String SUCCESS_STATUS = "success";

    @Value("${hmacSecret}")
    private String hmacSecret;

    @Override
    public SignatureGenerationResponseDto generateSignature(Map<String, String> parameters) {
        log.info("Получен запрос на генерацию подписи: {}", parameters);
        String sortedParamString = buildSortedParameterString(parameters);
        log.info("Параметры приведены к виду: " + sortedParamString);
        String signature = generateHmacSignature(sortedParamString, hmacSecret);
        log.info("Подпись: " + signature);

        return buildSuccessResponse(signature);
    }

    private String buildSortedParameterString(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue())
                .collect(Collectors.joining(PARAM_DELIMITER));
    }

    private SignatureGenerationResponseDto buildSuccessResponse(String signature) {
        SignatureDto signatureDto = new SignatureDto(signature);
        return new SignatureGenerationResponseDto(
                SUCCESS_STATUS,
                Collections.singletonList(signatureDto)
        );
    }

    private String generateHmacSignature(String data, String secret) {
        try {
            Mac mac = initializeMac(secret);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new SignatureGenerationException("При генерации подписи произошла ошибка: ", e);
        }
    }

    private Mac initializeMac(String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                HMAC_ALGORITHM
        );
        mac.init(secretKeySpec);
        return mac;
    }
}