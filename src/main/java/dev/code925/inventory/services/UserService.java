package dev.code925.inventory.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import dev.code925.inventory.models.dto.input.RegisterUser;
import dev.code925.inventory.models.dto.output.UserResponse;

public interface UserService extends UserDetailsService {

    public UserResponse register(final RegisterUser request);

}
