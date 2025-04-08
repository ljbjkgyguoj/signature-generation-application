package com.example.signaturecreationapplication.controller;

import com.example.signaturecreationapplication.dto.SignatureGenerationResponseDto;
import com.example.signaturecreationapplication.service.SignatureGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Контроллер для генерации HMAC SHA256 подписи.
 */
@RestController
@RequestMapping("signature")
@AllArgsConstructor
@Tag(name = "Генерация подписи", description = "Контроллер для генерации HMAC SHA256 подписи")
public class SignatureGenerationController {

    private final SignatureGenerationService signatureGenerationService;

    /**
     * Рест для генерации HMAC SHA256 подписи.
     *
     * @param operationId идентификатор операции
     * @param parameters  параметры запроса
     * @return {@link SignatureGenerationResponseDto} подпись
     */
    @Operation(summary = "Сгенерировать подпись")
    @PostMapping("/create/{operationId}")
    public ResponseEntity<SignatureGenerationResponseDto> generateSignature(@PathVariable Long operationId,
                                                                            @RequestBody Map<String, String> parameters) {
        return ResponseEntity.ok(signatureGenerationService.generateSignature(parameters));
    }
}
