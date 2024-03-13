package com.example.comp1640.repository;

import com.example.comp1640.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleInterface extends MongoRepository<Role, String> {
    Optional<Role> findById(String name);
}
