package com.example.signaturecreationapplication.controller;

import com.example.signaturecreationapplication.dto.SignatureDto;
import com.example.signaturecreationapplication.dto.SignatureGenerationResponseDto;
import com.example.signaturecreationapplication.service.SignatureGenerationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignatureGenerationController.class)
class SignatureGenerationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignatureGenerationService signatureGenerationService;

    private static final String VALID_TOKEN = "second-super-puper-secret";
    private static final String INVALID_TOKEN = "some-token";
    private static final String BASE_URL = "/signature/create/1111";
    private static final String REQUEST_BODY = "{\"sdfgh\":\"string\",\"pojkhvgh\":\"string\",\"additionalProp3\":\"string\"}";
    private static final String TOKEN = "Token";
    private static final SignatureGenerationResponseDto SIGNATURE_GENERATION_RESPONSE_DTO = new SignatureGenerationResponseDto(
            "success",
            Collections.singletonList(new SignatureDto("mocked-signature"))
    );

    @Test
    void generateSignature_withValidToken_shouldReturnSuccess() throws Exception {
        Mockito.when(signatureGenerationService.generateSignature(anyMap()))
                .thenReturn(SIGNATURE_GENERATION_RESPONSE_DTO);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .header(TOKEN, VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SIGNATURE_GENERATION_RESPONSE_DTO.getStatus()))
                .andExpect(jsonPath("$.result[0].signature").value(SIGNATURE_GENERATION_RESPONSE_DTO.getResult().get(0).getSignature()));
    }

    @Test
    void generateSignature_withInvalidToken_shouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .header(TOKEN, INVALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isForbidden());
    }

    @Test
    void generateSignature_withoutToken_shouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isForbidden());
    }
}