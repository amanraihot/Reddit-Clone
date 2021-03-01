package com.example.demo.repo;

import com.example.demo.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerficationRepo extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
}