package com.example.userservicemwfeve.repositories;

import com.example.userservicemwfeve.models.Token;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save( Token token);
    Optional<Token> findByValueAndDeletedEqualsAndExpiryAtGreaterThan(String value, Boolean isDeleted, Date expiryGreaterThan);


}
