package dev.code925.inventory.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.code925.inventory.models.Token;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllValidIsFalseOrRevokedIsFalseByOwnerId(Long id);

    Optional<Token> findByToken(String token);
}
