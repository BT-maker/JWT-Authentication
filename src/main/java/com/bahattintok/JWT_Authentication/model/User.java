// Kullanıcılar için JPA entity
package com.bahattintok.JWT_Authentication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity // JPA entity olarak işaretlenir
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"), // Kullanıcı adı benzersiz
           @UniqueConstraint(columnNames = "email") // E-posta benzersiz
       })
@Data // Getter, Setter, toString, equals, hashCode otomatik eklenir
@NoArgsConstructor // Parametresiz constructor
@AllArgsConstructor // Tüm alanlar için constructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan ID
    private Long id; // Kullanıcı ID
    
    @NotBlank
    @Size(max = 20)
    private String username; // Kullanıcı adı
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email; // Kullanıcı e-posta
    
    @NotBlank
    @Size(max = 120)
    private String password; // Şifre (hashlenmiş)
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(); // Kullanıcının rolleri
    
    // Kullanıcı oluşturmak için özel constructor
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
