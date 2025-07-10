// Rol veritabanı işlemleri için repository
package com.bahattintok.JWT_Authentication.repository;

import com.bahattintok.JWT_Authentication.model.Role;
import com.bahattintok.JWT_Authentication.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Spring Data JPA repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(RoleEnum name); // Rol adına göre arama
}
