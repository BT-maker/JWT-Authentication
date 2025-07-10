# JWT Authentication API

Bu proje, Spring Boot ile JWT tabanlı kimlik doğrulama ve yetkilendirme sağlayan bir REST API örneğidir. Kullanıcı kaydı, giriş, rol bazlı erişim ve Swagger/OpenAPI dokümantasyonu içerir.

## Özellikler
- JWT ile kimlik doğrulama
- Rol bazlı yetkilendirme (USER, ADMIN)
- Kullanıcı kaydı ve giriş
- Swagger UI ile API dokümantasyonu ve test

## Kullanılan Teknolojiler
- Java 21
- Spring Boot 3.5+
- Spring Security
- Spring Data JPA
- PostgreSQL
- springdoc-openapi (Swagger)

## Kurulum
1. Projeyi klonlayın:
   ```sh
   git clone <repo-url>
   cd JWT-Authentication
   ```
2. Veritabanı ayarlarını `src/main/resources/application.properties` dosyasında güncelleyin.
3. Maven ile bağımlılıkları yükleyin ve projeyi başlatın:
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
4. Uygulama çalıştığında Swagger UI'ya erişin:
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Swagger Kullanımı
- API endpointlerini görsel arayüzden test edebilirsiniz.
- JWT ile korunan endpointler için "Authorize" butonuna tıklayıp token girmeniz gerekir.

---

## Kısa GitHub Description Önerisi
> Spring Boot ile JWT tabanlı kimlik doğrulama ve rol bazlı yetkilendirme sağlayan REST API örneği. Swagger/OpenAPI desteğiyle birlikte. 