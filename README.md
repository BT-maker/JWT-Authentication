# JWT Authentication API

Bu proje, Spring Boot ile JWT tabanlı kimlik doğrulama ve yetkilendirme sağlayan bir REST API örneğidir. Kullanıcı kaydı, giriş, rol bazlı erişim ve Swagger/OpenAPI dokümantasyonu içerir.

## Özellikler
- JWT ile kimlik doğrulama
- Rol bazlı yetkilendirme (USER, ADMIN)
- Kullanıcı kaydı ve giriş
- Swagger UI ile API dokümantasyonu ve test
- PostgreSQL veritabanı ile kullanıcı ve rol yönetimi

## Kullanılan Teknolojiler
- Java 21
- Spring Boot 3.5+
- Spring Security
- Spring Data JPA
- PostgreSQL
- springdoc-openapi (Swagger)
- Lombok

## Kurulum
1. Projeyi klonlayın:
   ```sh
   git clone <repo-url>
   cd JWT-Authentication
   ```
2. Veritabanı ayarlarını `src/main/resources/application.properties` dosyasında güncelleyin:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/jwt_auth_db
   spring.datasource.username=[YOUR_USERNAME]
   spring.datasource.password=[YOUR_PASSWORD]
   ```
3. Maven ile bağımlılıkları yükleyin ve projeyi başlatın:
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
4. Uygulama çalıştığında Swagger UI'ya erişin:
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)



## Mimarî Katmanlar
- **Controller:** HTTP isteklerini karşılar, servisleri çağırır.
- **Service:** İş mantığı ve kimlik doğrulama işlemleri.
- **Repository:** Veritabanı işlemleri (Spring Data JPA).
- **Model/Entity:** Kullanıcı ve rol nesneleri.
- **DTO:** Veri transfer nesneleri (SignupRequest, LoginRequest, JwtResponse).
- **Security:** JWT filtreleri ve güvenlik ayarları.
- **Exception:** Hata yönetimi.

## Notlar
- Roller: Sadece `user` veya `admin` olarak gönderilmelidir. Sistem içinde bunlar `ROLE_USER` ve `ROLE_ADMIN` olarak tutulur.
- Varsayılan port: `8080`
- Swagger UI ile API uç noktalarını kolayca test edebilirsiniz.


