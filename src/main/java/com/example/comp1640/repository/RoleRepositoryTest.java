package com.example.comp1640.repository;

import com.example.comp1640.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepositoryTest extends MongoRepository<Role, String> {
}
