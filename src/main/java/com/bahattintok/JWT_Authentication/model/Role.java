// Kullanıcı rolleri için JPA entity
package com.bahattintok.JWT_Authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // JPA entity olarak işaretlenir
@Table(name = "roles") // Veritabanında 'roles' tablosu
@Data // Getter, Setter, toString, equals, hashCode otomatik eklenir
@NoArgsConstructor // Parametresiz constructor
@AllArgsConstructor // Tüm alanlar için constructor
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan ID
    private Long id; // Rol ID
    
    @Enumerated(EnumType.STRING) // Enum string olarak saklanır
    @Column(length = 20)
    private RoleEnum name; // Rol adı (enum)
}
