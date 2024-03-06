package com.example.comp1640.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.comp1640.model.AcademicYear;

public interface ItemRepository extends MongoRepository<AcademicYear, String> {
	
	@Query("{name:'?0'}")
	AcademicYear findItemByName(String name);
	
	@Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<AcademicYear> findAll(String category);
	
	public long count();

}