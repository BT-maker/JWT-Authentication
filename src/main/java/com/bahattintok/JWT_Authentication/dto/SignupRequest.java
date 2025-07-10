// Kayıt isteği için DTO (Data Transfer Object)
package com.bahattintok.JWT_Authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data // Getter, Setter, toString, equals, hashCode otomatik eklenir
public class SignupRequest {
    
    @NotBlank // Boş olamaz
    @Size(min = 3, max = 20) // Uzunluk kontrolü
    private String username; // Kullanıcı adı
    
    @NotBlank // Boş olamaz
    @Size(max = 50) // Maksimum uzunluk
    @Email // E-posta formatı kontrolü
    private String email; // Kullanıcı e-posta
    
    @NotBlank // Boş olamaz
    @Size(min = 6, max = 40) // Uzunluk kontrolü
    private String password; // Şifre
    
    private Set<String> roles; // Kullanıcı rolleri
}
