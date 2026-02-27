package com.snyl.scentanyl.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")
    public Map<String, String> getToken(HttpServletRequest request) {
        String clientIp = getClientIP(request);
        String token = tokenService.generateToken(clientIp);
        return Map.of("token", token);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}