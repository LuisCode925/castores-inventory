package dev.code925.inventory.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.code925.inventory.models.Token;
import dev.code925.inventory.models.User;
import dev.code925.inventory.repositories.TokenRepository;
import dev.code925.inventory.repositories.UserRepository;
import dev.code925.inventory.services.authentication.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Bypass para CORS - Preflight request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bypass para Endpoints de Autenticación
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extracción del Token
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // filterChain.doFilter(request, response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "El Token no existe o no esta presente.");
            return;
        }

        final String jwtToken = authHeader.substring(7);

        // Extracción del Usuario y Verificación de Contexto
        final String userEmail = jwtService.extractUsername(jwtToken);// INFO: username en realidad devuelve el email.

        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Verificación del Token y de su estado
        final Token token = tokenRepository.findByToken(jwtToken).orElse(null);

        if (token == null || token.getExpired() || token.getRevoked()) {
            // filterChain.doFilter(request, response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "El Token ya no es valido.");
            return;
        }

        // Verificación de Usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        final Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        if (user.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validación Final del JWT (CRITICO)
        final boolean isTokenValid = jwtService.isTokenValid(jwtToken, user.get());
        if (!isTokenValid) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        // Establecimiento del Contexto de Seguridad
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);

    }
}
