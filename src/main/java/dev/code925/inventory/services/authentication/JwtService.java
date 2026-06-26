package dev.code925.inventory.services.authentication;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.code925.inventory.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    public String generateToken(final User user) {
        return this.buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(final User user) {
        return this.buildToken(user, refreshExpiration);
    }

    private String buildToken(final User user, final Long expiration) {
        // solo cambia el tiempo de expiración
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("name", user.getName()))
                .subject(user.getEmail()) // TODO: cambiar a username, email es una mala practica
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        // return new SecretKeySpec(secretKey.getBytes(), "AES");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String refreshToken) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();

        return claims.getSubject();
    }

    public Date extractExpiration(String refreshToken) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();

        return claims.getExpiration();
    }

    private boolean isTokenExpired(String refreshToken) {
        return extractExpiration(refreshToken).before(new Date());
    }

    public boolean isTokenValid(final String refreshToken, final User user) {
        final String username = extractUsername(refreshToken);
        return (username.equals(user.getEmail())) && !isTokenExpired(refreshToken);
    }

}
