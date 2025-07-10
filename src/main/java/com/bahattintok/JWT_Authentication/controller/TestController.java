// Rol bazlı erişim testleri için controller
package com.bahattintok.JWT_Authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600) // Tüm domainlerden istek kabul edilir
@RestController // Controller olduğunu belirtir
@RequestMapping("/api/test") // Tüm endpointler /api/test ile başlar
public class TestController {
    
    @GetMapping("/all") // Herkese açık endpoint
    public String allAccess() {
        return "Public Content.";
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // USER veya ADMIN rolü olanlar erişebilir
    public String userAccess() {
        return "User Content.";
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Sadece ADMIN rolü olanlar erişebilir
    public String adminAccess() {
        return "Admin Board.";
    }
}
