package com.example.comp1640;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.comp1640.Store.StorageService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.AcademicYearRepository;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FacultyRepository;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableMongoRepositories
public class Comp1640Application implements CommandLineRunner{

	@Autowired
	FalcultyRepository facultyRepository;

	public static void main(String[] args) {
		SpringApplication.run(Comp1640Application.class, args);

	}
	public void create(){
		facultyRepository.CreateFalcuty("02","IT","Computer","01");
	}


	@Override
	public void run(String... args) throws Exception {

	}
}

