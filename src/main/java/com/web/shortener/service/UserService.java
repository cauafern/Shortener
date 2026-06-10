package com.web.shortener.service;

import com.web.shortener.model.Url;
import com.web.shortener.model.User;
import com.web.shortener.repository.UrlRepository;
import com.web.shortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UrlRepository urlRepository;

    public void register(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setCreateAt(LocalDateTime.now());
        userRepository.save(newUser);
    }

    public User login(String email, String password) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Email not exists");
        }
        User user = userRepository.findByEmail(email).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        return user;
    }

    public User searchLoggedInUser() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UserDetails loadUserByUsername(@NonNull String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public @Nullable UserDetails updatePassword(UserDetails user, @Nullable String newPassword) {
        return null;
    }

}
