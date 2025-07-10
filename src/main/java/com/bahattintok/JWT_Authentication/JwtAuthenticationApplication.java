// Uygulamanın ana başlangıç noktası
package com.bahattintok.JWT_Authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Spring Boot ana uygulama sınıfı
public class JwtAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args); // Uygulamayı başlatır
	}

}