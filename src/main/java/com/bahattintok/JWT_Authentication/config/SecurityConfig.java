// Spring Security ana konfigürasyon sınıfı
package com.bahattintok.JWT_Authentication.config;

import com.bahattintok.JWT_Authentication.security.jwt.AuthEntryPointJwt;
import com.bahattintok.JWT_Authentication.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Spring konfigürasyon sınıfı
@EnableWebSecurity // Web güvenliği aktif
@EnableMethodSecurity // Yöntem bazlı güvenlik aktif
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Kullanıcı detay servisi

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Yetkisiz girişlerde çalışacak handler

    @Autowired
    private JwtAuthFilter jwtAuthFilter; // JWT filtre

    @Bean
    @SuppressWarnings("deprecation")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Kullanıcı detay servisi ve şifreleyici ayarlanıyor
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // AuthenticationManager bean'i
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Şifreleri BCrypt ile şifreler
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF koruması devre dışı
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Yetkisiz girişlerde custom handler
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless oturum yönetimi
                .authorizeHttpRequests(auth -> auth
                        // Swagger ve public endpointler için izin ver
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/swagger-resources",
                                "/configuration/ui",
                                "/configuration/security",
                                "/webjars/**",
                                "/api/auth/**",
                                "/api/secure/public")
                        .permitAll()
                        .anyRequest().authenticated()); // Diğer tüm istekler için kimlik doğrulama zorunlu

        http.authenticationProvider(authenticationProvider()); // AuthenticationProvider ekleniyor
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtre ekleniyor
        return http.build();
    }
}
