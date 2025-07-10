// Kimlik doğrulama ve kayıt işlemlerinin servis implementasyonu
package com.bahattintok.JWT_Authentication.security.services;

import com.bahattintok.JWT_Authentication.dto.JwtResponse;
import com.bahattintok.JWT_Authentication.dto.LoginRequest;
import com.bahattintok.JWT_Authentication.dto.SignupRequest;
import com.bahattintok.JWT_Authentication.exception.BadRequestException;
import com.bahattintok.JWT_Authentication.model.Role;
import com.bahattintok.JWT_Authentication.model.RoleEnum;
import com.bahattintok.JWT_Authentication.model.User;
import com.bahattintok.JWT_Authentication.repository.RoleRepository;
import com.bahattintok.JWT_Authentication.repository.UserRepository;
import com.bahattintok.JWT_Authentication.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service // Spring servis katmanı
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager; // Spring Security authentication yöneticisi
    
    @Autowired
    private UserRepository userRepository; // Kullanıcı repository
    
    @Autowired
    private RoleRepository roleRepository; // Rol repository
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Şifreleyici
    
    @Autowired
    private JwtUtils jwtUtils; // JWT yardımcı sınıfı
    
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())); // Giriş doğrulama
        
        SecurityContextHolder.getContext().setAuthentication(authentication); // Kimlik doğrulama context'e eklenir
        String jwt = jwtUtils.generateJwtToken(authentication); // JWT token oluşturulur
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles); // JWT yanıtı döner
    }
    
    @Override
    public void registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new BadRequestException("Error: Username is already taken!"); // Kullanıcı adı kontrolü
        }
        
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!"); // E-posta kontrolü
        }
        
        // Yeni kullanıcı oluşturulur
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));
        
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equalsIgnoreCase("admin")) {
                    Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if (role.equalsIgnoreCase("user")) {
                    Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        
        user.setRoles(roles); // Roller atanır
        userRepository.save(user); // Kullanıcı kaydedilir
    }
}
