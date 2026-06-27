package dev.code925.inventory.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.code925.inventory.exceptions.TokenNotFoundException;
import dev.code925.inventory.models.Token;
import dev.code925.inventory.models.Transaction;
import dev.code925.inventory.models.dto.input.CreateTransaction;
import dev.code925.inventory.models.dto.output.TransactionResponse;
import dev.code925.inventory.models.enums.TransactionType;
import dev.code925.inventory.repositories.TokenRepository;
import dev.code925.inventory.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TokenRepository tokenRepository;

    public Transaction recordMovement(final CreateTransaction request, final String token) {

        String rawToken = token.replace("Bearer ", "").trim();
        Token searchedToken = tokenRepository.findByToken(rawToken)
                .orElseThrow(() -> new TokenNotFoundException("El token: " + token + " no ha sido encontrado."));

        Transaction transaction = Transaction.builder()
                .product(request.getProduct())
                .quantity(request.getQuantity())
                .responsible(searchedToken.getOwner())
                .movement(request.getType())
                .registeredAt(OffsetDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    public List<TransactionResponse> allTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> TransactionResponse.builder()
                        .id(transaction.getId())
                        .product(transaction.getProduct().getName())
                        .quantity(transaction.getQuantity())
                        .responsible(transaction.getResponsible().getName())
                        .movement(transaction.getMovement().name())
                        .registeredAt(transaction.getRegisteredAt())
                        .build())
                .toList();
    }

    public List<TransactionResponse> filterTransactions(final String name) throws Exception {
        if (name == null || (!name.equalsIgnoreCase("inflow") && !name.equalsIgnoreCase("outflow"))) {
            throw new Exception("Solo se permiten valores de tipo 'inflow' y 'outflow'.");
        }
        TransactionType transactionType = TransactionType.valueOf(name.toUpperCase().trim());
        return transactionRepository.findByMovement(transactionType).stream()
                .map(transaction -> TransactionResponse.builder()
                        .id(transaction.getId())
                        .product(transaction.getProduct().getName())
                        .quantity(transaction.getQuantity())
                        .responsible(transaction.getResponsible().getName())
                        .movement(transaction.getMovement().name())
                        .registeredAt(transaction.getRegisteredAt())
                        .build())
                .toList();
    }

}
