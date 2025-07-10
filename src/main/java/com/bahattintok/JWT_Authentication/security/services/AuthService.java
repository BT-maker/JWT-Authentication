// Kimlik doğrulama ve kayıt işlemleri için servis arayüzü
package com.bahattintok.JWT_Authentication.security.services;

import com.bahattintok.JWT_Authentication.dto.JwtResponse;
import com.bahattintok.JWT_Authentication.dto.LoginRequest;
import com.bahattintok.JWT_Authentication.dto.SignupRequest;

public interface AuthService {
    
    JwtResponse authenticateUser(LoginRequest loginRequest); // Giriş işlemi
    
    void registerUser(SignupRequest signupRequest); // Kayıt işlemi
}
