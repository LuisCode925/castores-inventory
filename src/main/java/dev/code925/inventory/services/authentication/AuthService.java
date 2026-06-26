package dev.code925.inventory.services.authentication;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.code925.inventory.models.Role;
import dev.code925.inventory.models.Token;
import dev.code925.inventory.models.User;
import dev.code925.inventory.models.dto.input.LoginUser;
import dev.code925.inventory.models.dto.input.RegisterUser;
import dev.code925.inventory.models.dto.output.TokenResponse;
import dev.code925.inventory.models.enums.Roles;
import dev.code925.inventory.models.enums.TokenType;
import dev.code925.inventory.repositories.RoleRepository;
import dev.code925.inventory.repositories.TokenRepository;
import dev.code925.inventory.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public TokenResponse register(RegisterUser request) {
        Role defaultRole = roleRepository.findByName(Roles.ADMINISTRATOR.name()).orElseThrow();

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(defaultRole)
                .status(true)
                .build();

        User savedUser = userRepository.save(user);

        // Generación de ambos tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginUser request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User searchedUser = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(searchedUser);
        String refreshToken = jwtService.generateRefreshToken(searchedUser);

        this.revokeAllUserTokens(searchedUser);
        saveUserToken(searchedUser, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByOwnerId(user.getId());

        if (!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.contains("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        final User user = userRepository.findByEmail(userEmail).orElseThrow();

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);

        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token newToken = Token.builder()
                .owner(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(newToken);
    }
}
