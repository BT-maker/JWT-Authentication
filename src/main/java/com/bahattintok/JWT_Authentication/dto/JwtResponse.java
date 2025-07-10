// JWT yanıtı için DTO (Data Transfer Object)
package com.bahattintok.JWT_Authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Getter, Setter, toString, equals, hashCode otomatik eklenir
@NoArgsConstructor // Parametresiz constructor
@AllArgsConstructor // Tüm alanlar için constructor
public class JwtResponse {
    
    private String token; // JWT token
    private String type = "Bearer"; // Token tipi
    private Long id; // Kullanıcı ID
    private String username; // Kullanıcı adı
    private String email; // Kullanıcı e-posta
    private List<String> roles; // Kullanıcı rolleri
    
    // Token üretimi sonrası kullanılacak özel constructor
    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
