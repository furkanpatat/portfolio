package com.furkanpatat.portfolio.security;

import com.furkanpatat.portfolio.entity.AdminUser;
import com.furkanpatat.portfolio.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(auth -> auth
                        // HERKESE AÇIK (PUBLIC) UÇ NOKTALAR:
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/projects/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/experiences/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/skills/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/messages").permitAll()
                        .requestMatchers("/api/visitors/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/articles/**").permitAll()
                        // GERİ KALAN HER ŞEY (Mesaj okuma, Proje ekleme/silme vb.) KİLİTLİ:
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Şifreleri çözülemez BCrypt algoritmasıyla veri tabanına kaydetmek için
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ÇOK ÖNEMLİ: Proje ilk kez ayağa kalktığında veritabanında hiç admin yoksa,
    // otomatik olarak furkan kullanıcısını oluşturur.
    @Bean
    public CommandLineRunner initAdmin(AdminUserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                AdminUser admin = AdminUser.builder()
                        .username("furkan")
                        .password(passwordEncoder.encode("12345")) // Varsayılan Şifre: 12345
                        .build();
                repository.save(admin);
                System.out.println("✅ Sistem başlatıldı. Varsayılan Admin eklendi! Kullanıcı adı: furkan | Şifre: 12345");
            }
        };
    }
}