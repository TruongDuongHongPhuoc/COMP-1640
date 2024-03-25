package com.example.comp1640.Service;

import com.example.comp1640.Store.FileSystemStorageService;
import com.example.comp1640.Store.FileUploadController;
import com.example.comp1640.Store.StorageService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ContributionService {
    @Autowired
    ContributionRepository contributionRepository;
    @Autowired
    FileSystemStorageService StoreService;

    public void storeFile(MultipartFile file){
        StoreService.store(file);
    }
    public void CreateContribution(String id, String name, String description, LocalDate submitDate, int status, String accountId, String academicYearId, String facultyId, MultipartFile file){
        storeFile(file);
        System.out.println("File is Stored");
        String path = file.getOriginalFilename();
        System.out.println("Get file path");
        contributionRepository.CreateContribution(id,name,description,submitDate,status,accountId,academicYearId,facultyId,path);
        System.out.println("Created Contribution");
    }

    public List<String> ReturnALlFile(){
       List<String> lists = StoreService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());
        return null;
    }
    public List<Contribution> ReturnPublicContribution(){
        List<Contribution> cons = contributionRepository.ReturnContributions();
        List<Contribution> filteredList = cons.stream()
                .filter(con -> con.getStatus() != 2 && con.getStatus() != 0 && con.getStatus() != 1)
                .collect(Collectors.toList());
        return filteredList;
    }
    public List<Contribution> ReturnForMarketingManager(){
        List<Contribution> cons = contributionRepository.ReturnContributions();
        List<Contribution> filteredList = cons.stream()
                .filter(con -> con.getStatus() != 2 && con.getStatus() != 0)
                .collect(Collectors.toList());
        return filteredList;
    }
    public List<Contribution> ReturnAllContribution(){
        return contributionRepository.ReturnContributions();
    }
    public void deletefile(String file){
        StoreService.deleteFile(file);
    }
    public void UpdateContribution(String id,String name,String description,LocalDateTime submitDate,int status,String accountId,String academicYearId, String facultyId, MultipartFile file, String oldfile){
        System.out.println("Update contribution service Run");
        StoreService.deleteFile(oldfile);
        System.out.println("old file deleted");
        contributionRepository.UpdateContribution(id,name,description,submitDate,status,accountId,academicYearId,facultyId,file.getOriginalFilename());
        System.out.println("Contribution repository updated");
        StoreService.store(file);
        System.out.println("Store service store file");
    }
}
