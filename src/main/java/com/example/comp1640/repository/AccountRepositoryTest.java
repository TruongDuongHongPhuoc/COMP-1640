package com.example.comp1640.repository;

import com.example.comp1640.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepositoryTest extends MongoRepository<Account, String> {
    Optional<Account> findByMail(String mail);
}
