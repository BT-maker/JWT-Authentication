// Kullanıcı veritabanı işlemleri için repository
package com.bahattintok.JWT_Authentication.repository;

import com.bahattintok.JWT_Authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Spring Data JPA repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username); // Kullanıcı adına göre arama
    
    Boolean existsByUsername(String username); // Kullanıcı adı var mı?
    
    Boolean existsByEmail(String email); // E-posta var mı?
}
