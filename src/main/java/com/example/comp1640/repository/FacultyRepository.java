package com.example.comp1640.repository;

import com.example.comp1640.model.Faculty;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacultyRepository extends MongoRepository<Faculty, String> {
}
