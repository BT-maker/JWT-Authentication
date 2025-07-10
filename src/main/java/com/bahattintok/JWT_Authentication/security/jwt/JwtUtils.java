// JWT token işlemleri için yardımcı sınıf
package com.bahattintok.JWT_Authentication.security.jwt;

import com.bahattintok.JWT_Authentication.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // Spring bean olarak tanımlanır
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class); // Loglama

    @Value("${jwt.secret}")
    private String jwtSecret; // JWT imzalama anahtarı

    @Value("${jwt.expiration}")
    private int jwtExpirationMs; // Token geçerlilik süresi (ms)

    public Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes()); // Anahtar üretimi
    }

    // JWT token oluşturur
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Kullanıcı adı
                .setIssuedAt(new Date()) // Oluşturulma zamanı
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Bitiş zamanı
                .signWith(key()) // İmzalama
                .compact();
    }

    // Token'dan kullanıcı adını alır
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Token geçerli mi kontrolü
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
