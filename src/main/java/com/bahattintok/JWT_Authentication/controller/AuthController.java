// Kimlik doğrulama işlemleri için controller
package com.bahattintok.JWT_Authentication.controller;

import com.bahattintok.JWT_Authentication.dto.JwtResponse;
import com.bahattintok.JWT_Authentication.dto.LoginRequest;
import com.bahattintok.JWT_Authentication.dto.SignupRequest;
import com.bahattintok.JWT_Authentication.security.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600) // Tüm domainlerden istek kabul edilir
@RestController // Controller olduğunu belirtir
@RequestMapping("/api/auth") // Tüm endpointler /api/auth ile başlar
public class AuthController {
    
    @Autowired
    private AuthService authService; // Kimlik doğrulama servis katmanı
    
    @PostMapping("/signin") // Giriş endpointi
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest); // Giriş işlemi
        return ResponseEntity.ok(jwtResponse); // JWT ile cevap dön
    }
    
    @PostMapping("/signup") // Kayıt endpointi
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        authService.registerUser(signupRequest); // Kayıt işlemi
        return ResponseEntity.ok("User registered successfully!"); // Başarılı kayıt mesajı
    }
}
