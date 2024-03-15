package com.example.comp1640.repository;

import com.example.comp1640.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepositoryTest extends MongoRepository<Account, String> {
    Optional<Account> findAccountByMail(String mail);
    public Account findByName(String name);

}
