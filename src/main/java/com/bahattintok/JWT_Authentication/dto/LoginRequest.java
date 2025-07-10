// Giriş isteği için DTO (Data Transfer Object)
package com.bahattintok.JWT_Authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Getter, Setter, toString, equals, hashCode otomatik eklenir
public class LoginRequest {
    
    @NotBlank // Boş olamaz
    private String username; // Kullanıcı adı
    
    @NotBlank // Boş olamaz
    private String password; // Şifre
}
