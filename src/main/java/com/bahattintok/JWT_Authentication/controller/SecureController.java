// Güvenli ve herkese açık endpointler için controller
package com.bahattintok.JWT_Authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Controller olduğunu belirtir
@RequestMapping("/api/secure") // Tüm endpointler /api/secure ile başlar
public class SecureController {
    @GetMapping("/public") // Herkese açık endpoint
    public String publicAccess() {
        return "Public Content: Herkes görebilir.";
    }

    @GetMapping("/authenticated") // Sadece giriş yapmış kullanıcılar erişebilir
    public String authenticatedAccess() {
        return "Authenticated Content: Giriş yapmış herkes görebilir.";
    }
} 