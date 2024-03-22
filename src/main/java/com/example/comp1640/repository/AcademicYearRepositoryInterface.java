package com.example.comp1640.repository;

import com.example.comp1640.model.AcademicYear;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AcademicYearRepositoryInterface extends MongoRepository<AcademicYear, String> {
}
