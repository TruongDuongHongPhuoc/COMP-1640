package com.example.comp1640.Service;

import com.example.comp1640.Store.FileSystemStorageService;
import com.example.comp1640.Store.FileUploadController;
import com.example.comp1640.Store.StorageService;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("File is Stored");
        String path = file.getOriginalFilename();
        System.out.println("Get file path");
        contributionRepository.CreateContribution(id,name,description,submitDate,approve,isPublic,accountId,academicYearId,path);
        System.out.println("Created Contribution");
    }

    public List<String> ReturnALlFile(){
       List<String> lists = StoreService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());
        return null;
    }
    public void deletefile(String file){
        StoreService.deleteFile(file);
    }
    public void UpdateContribution(String id,String name,String description,String submitDate,Boolean approve,Boolean isPublic,String accountId,String academicYearId, MultipartFile file, String oldfile){
        StoreService.deleteFile(oldfile);
        contributionRepository.UpdateContribution(id,name,description,submitDate,approve,isPublic,accountId,academicYearId,file.getOriginalFilename());
        StoreService.store(file);
    }
}
