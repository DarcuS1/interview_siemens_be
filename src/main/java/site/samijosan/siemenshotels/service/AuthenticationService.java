package site.samijosan.siemenshotels.service;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.samijosan.siemenshotels.model.User;
import site.samijosan.siemenshotels.repository.UserRepository;
import site.samijosan.siemenshotels.request.AuthenticationRequest;
import site.samijosan.siemenshotels.request.RegisterRequest;
import site.samijosan.siemenshotels.response.AuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostConstruct
    private void generateAdmin() {
        if(repository.findUserByEmail("admin@admin.com").isPresent()) {
            return;
        }
        AuthenticationResponse res = registerAdmin();
    }

    public AuthenticationResponse registerAdmin() {
        try {
            var user = User.builder()
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            repository.save(user);

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException ex) {
            return AuthenticationResponse.builder()
                    .error("Failed to create admin account")
                    .build();
        }
    }

    public AuthenticationResponse registerUser(RegisterRequest request) {

        try {
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException ex) {
            return AuthenticationResponse.builder()
                    .error("A player with the same email: '" + request.getEmail() + "' already exists")
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findUserByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
