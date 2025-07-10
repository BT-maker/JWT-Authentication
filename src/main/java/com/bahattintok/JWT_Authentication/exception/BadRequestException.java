// Özel bad request (400) hatası için exception sınıfı
package com.bahattintok.JWT_Authentication.exception;

public class BadRequestException extends RuntimeException {
    
    // Sadece mesaj ile exception oluşturur
    public BadRequestException(String message) {
        super(message);
    }
    
    // Mesaj ve sebep (cause) ile exception oluşturur
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
} 