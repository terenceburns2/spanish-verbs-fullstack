package com.terenceapps.spanishverbs.repository;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRespository {
    Optional<UserDetails> findByEmail(String email);
    void registerUser(String email, String password);
}
