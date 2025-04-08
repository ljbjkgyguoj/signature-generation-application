package com.example.signaturecreationapplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Подпись")
public class SignatureDto {

    @Schema(description = "Подпись в виде строки")
    String signature;
}
