package com.example.comp1640.repository;

import com.example.comp1640.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhuocAccountRepo extends MongoRepository<Account, String> {
}
