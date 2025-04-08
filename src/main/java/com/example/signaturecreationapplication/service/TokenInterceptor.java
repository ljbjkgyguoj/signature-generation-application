package com.example.signaturecreationapplication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Перехватчик запросов для проверки наличия токена.
 */
@Service
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${tokenSecret}")
    private String expectedToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Token");
        if (token != null && token.equals(expectedToken)) {
            return true;
        } else {
            log.error("В заголовках запроса отсутствует или неправильно передан Token: " + token);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }
}
