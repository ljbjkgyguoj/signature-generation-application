package com.example.signaturecreationapplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ на запрос генерации подписи")
public class SignatureGenerationResponseDto {

    @Schema(description = "Статус")
    String status;

    @Schema(description = "Подпись")
    List<SignatureDto> result;
}
