package dev.code925.inventory.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.code925.inventory.models.Role;
import dev.code925.inventory.models.User;
import dev.code925.inventory.models.dto.input.RegisterUser;
import dev.code925.inventory.models.dto.output.UserResponse;
import dev.code925.inventory.models.enums.Roles;
import dev.code925.inventory.repositories.RoleRepository;
import dev.code925.inventory.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        public UserResponse register(final RegisterUser request) {
                Role defaultRole = roleRepository.findByName(Roles.USER.name()).orElseThrow();
                User user = User.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword())) // Se hashea la constraseña
                                                                                         // para almacenarla.
                                .role(defaultRole)
                                .status(true)
                                .build();

                User userSaved = userRepository.save(user);

                return UserResponse.builder()
                                .id(userSaved.getId())
                                .name(userSaved.getName())
                                .email(userSaved.getEmail())
                                .role(userSaved.getRole().getName())
                                .status(userSaved.getStatus())
                                .build();
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User searchedUser = userRepository.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                return org.springframework.security.core.userdetails.User.builder()
                                .username(searchedUser.getEmail())
                                .password(searchedUser.getPassword())
                                .authorities(new SimpleGrantedAuthority("ROLE_" + searchedUser.getRole().getName()))
                                .build();
        }

}