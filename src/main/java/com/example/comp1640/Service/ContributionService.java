package com.example.comp1640.Service;

import com.example.comp1640.Store.FileSystemStorageService;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContributionService {
    @Autowired
    ContributionRepository contributionRepository;
    @Autowired
    FileSystemStorageService StoreService;

    public void storeFile(MultipartFile file){
        StoreService.store(file);
    }
    public void CreateContribution(String id,String name,String description,String submitDate,Boolean approve,Boolean isPublic,String accountId,String academicYearId, MultipartFile file){
        storeFile(file);
        String path = file.getOriginalFilename();
        contributionRepository.CreateContribution(id,name,description,submitDate,approve,isPublic,accountId,academicYearId,path);
    }

}
