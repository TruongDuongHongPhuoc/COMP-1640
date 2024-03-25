package com.example.comp1640.repository;

import com.example.comp1640.model.Contribution;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContributionRepositoryInterface extends MongoRepository<Contribution, String> {
}
