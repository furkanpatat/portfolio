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

// CORS için eklenen importlar:
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // BURAYI GÜNCELLEDİK: Artık aşağıdaki corsConfigurationSource metoduna bakacak
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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

    // CORS İZİNLERİNİ BURADA TANIMLIYORUZ
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // İzin verilen linkleri (Localhost, Vercel ve Yeni Domain) buraya yazıyoruz
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://portfolio-frontend-livid-xi.vercel.app", // Vercel'in orijinal linki
                "https://furkanpatat.com",                        // YENİ ALAN ADIN
                "https://www.furkanpatat.com"                     // YENİ ALAN ADININ WWW'LU HALİ
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initAdmin(AdminUserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                AdminUser admin = AdminUser.builder()
                        .username("furkan")
                        .password(passwordEncoder.encode("12345"))
                        .build();
                repository.save(admin);
                System.out.println("✅ Sistem başlatıldı. Varsayılan Admin eklendi! Kullanıcı adı: furkan | Şifre: 12345");
            }
        };
    }
}
