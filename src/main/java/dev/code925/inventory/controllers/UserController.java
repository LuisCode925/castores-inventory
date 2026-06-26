package dev.code925.inventory.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.code925.inventory.models.dto.input.RegisterUser;
import dev.code925.inventory.models.dto.output.UserResponse;
import dev.code925.inventory.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUser request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
    }

}
