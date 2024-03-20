package com.example.comp1640.repository;

import com.example.comp1640.model.Account;
import com.example.comp1640.model.Faculty;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FalcutyRepository extends MongoRepository<Faculty, String> {
}
