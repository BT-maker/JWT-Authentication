package com.bahattintok.JWT_Authentication.config;

import com.bahattintok.JWT_Authentication.security.jwt.JwtUtils;
import com.bahattintok.JWT_Authentication.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Arrays;

// JWT filtre sınıfı, her istekte çalışır
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils; // JWT işlemleri için yardımcı sınıf

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Kullanıcı detay servisi

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class); // Loglama

    private static final List<String> SWAGGER_WHITELIST = Arrays.asList(
        "/v3/api-docs", "/v3/api-docs/", "/v3/api-docs/swagger-config",
        "/swagger-ui", "/swagger-ui/", "/swagger-ui.html",
        "/swagger-resources", "/swagger-resources/", "/swagger-resources/configuration/ui",
        "/swagger-resources/configuration/security", "/webjars/"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI(); // İstek yapılan path
        // Swagger ve OpenAPI endpointlerini filtre dışı bırak
        if (SWAGGER_WHITELIST.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            System.out.println("JwtAuthFilter ÇALIŞTI! Path: " + request.getRequestURI());
            String jwt = parseJwt(request); // Header'dan JWT token'ı al
            System.out.println("JWT: " + jwt);
            // Token varsa ve geçerliyse kullanıcıyı doğrula
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt); // Token'dan kullanıcı adını al
                System.out.println("JWT ile gelen username: " + username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Kullanıcı detaylarını
                                                                                           // yükle
                System.out.println("UserDetails: " + userDetails.getUsername() + " | Authorities: "
                        + userDetails.getAuthorities());
                // Kimlik doğrulama nesnesi oluştur
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication); // Kullanıcıyı güvenlik
                                                                                      // context'ine ekle
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e); // Hata logla
        }
        filterChain.doFilter(request, response); // Filtre zincirine devam et
    }

    // Authorization header'dan JWT token'ı ayıkla
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // "Bearer " sonrası token'ı döndür
        }

        return null;
    }
}
