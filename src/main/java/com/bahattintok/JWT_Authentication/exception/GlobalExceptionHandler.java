// Tüm uygulama genelinde exception yönetimi için handler
package com.bahattintok.JWT_Authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Tüm controller'lar için global exception handler
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BadRequestException.class) // BadRequestException yakalanır
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error); // 400 Bad Request döner
    }
    
    @ExceptionHandler(BadCredentialsException.class) // Yanlış kullanıcı adı/şifre
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); // 401 Unauthorized döner
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class) // Validation hataları
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors); // 400 Bad Request döner
    }
    
    @ExceptionHandler(AccessDeniedException.class) // Yetkisiz erişim
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Access Denied");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error); // 403 Forbidden döner
    }
    
    @ExceptionHandler(Exception.class) // Diğer tüm hatalar
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error); // 500 Internal Server Error döner
    }
}
