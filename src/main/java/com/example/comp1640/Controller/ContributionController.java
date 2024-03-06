package com.example.comp1640.Controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.comp1640.repository.ContributionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ContributionController {
    @Autowired
    ContributionRepository ContributeRepo;

    @PostMapping("/Create")
    public String postMethodName(@RequestParam ("id") String id, @RequestParam ("name") String name, @RequestParam ("TypeOfFile") String typeOfFile, 
    @RequestParam ("SubmitDate") Date submitDate, @RequestParam("IsPublic") Boolean isPublic, @RequestParam ("accountID") int accountID, @RequestParam ("academicYearID") int academicYearID) {
        
        ContributeRepo.CreateContribution(id, name, typeOfFile, null, isPublic, accountID, academicYearID);
        return "Admin";
    }
    
    @GetMapping("/CreateContribution")
    public String getMethodName(@RequestParam String param) {
        return "CreateContribution";
    }
    
}
