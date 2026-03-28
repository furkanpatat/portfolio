package com.furkanpatat.portfolio.controller;

import com.furkanpatat.portfolio.dto.AuthRequest;
import com.furkanpatat.portfolio.dto.AuthResponse;
import com.furkanpatat.portfolio.entity.AdminUser;
import com.furkanpatat.portfolio.repository.AdminUserRepository;
import com.furkanpatat.portfolio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // Veritabanında bu kullanıcı var mı bakıyoruz
        Optional<AdminUser> adminOpt = adminUserRepository.findByUsername(request.getUsername());

        // Kullanıcı varsa ve şifresi DB'deki şifrelenmiş haliye eşleşiyorsa
        if (adminOpt.isPresent() && passwordEncoder.matches(request.getPassword(), adminOpt.get().getPassword())) {
            String token = jwtService.generateToken(request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token)); // Al sana bilet!
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hatalı kullanıcı adı veya şifre!");
    }
}