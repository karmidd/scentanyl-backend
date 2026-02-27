package com.snyl.scentanyl.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/token")
    public ResponseEntity<?> getToken(HttpServletRequest request) {
        // Check Origin header first, fall back to Referer
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");

        if (!isAllowedOrigin(origin) && !isAllowedReferer(referer)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Unauthorized origin"));
        }

        String clientIp = getClientIP(request);
        String token = tokenService.generateToken(clientIp);
        return ResponseEntity.ok(Map.of("token", token));
    }

    private boolean isAllowedOrigin(String origin) {
        if (origin == null) return false;
        for (String allowed : frontendUrl.split(",")) {
            if (origin.equals(allowed.trim())) return true;
        }
        return false;
    }

    private boolean isAllowedReferer(String referer) {
        if (referer == null) return false;
        for (String allowed : frontendUrl.split(",")) {
            if (referer.startsWith(allowed.trim())) return true;
        }
        return false;
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}