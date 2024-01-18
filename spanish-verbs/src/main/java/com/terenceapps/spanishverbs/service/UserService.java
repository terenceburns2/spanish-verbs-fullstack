package com.terenceapps.spanishverbs.service;

import com.terenceapps.spanishverbs.exception.EmailExistsException;
import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.repository.JdbcUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final JdbcUserRepository userRepository;

    public UserService(JdbcUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        if ((userRepository.findByEmail(user.getEmail())).isPresent()) {
            throw new EmailExistsException("This email already exists. Try signing in.");
        }
        userRepository.registerUser(user.getEmail(), passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User email cannot be found.");
        }

        return user.get();
    }
}
