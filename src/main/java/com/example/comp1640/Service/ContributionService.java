package com.example.comp1640.Service;

import com.example.comp1640.Store.FileSystemStorageService;
import com.example.comp1640.Store.FileUploadController;
import com.example.comp1640.Store.StorageService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
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
    AcademicYearRepositoryInterface academicYearRepository;
    @Autowired
    FileSystemStorageService StoreService;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    AcademicYearRepositoryInterface academicYearRepositoryInterface;

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
        return attachingInfor(filteredList);
    }
    public List<Contribution> ReturnAllContribution(){
        return contributionRepository.ReturnContributions().stream().map(c->{
            AcademicYear ac = academicYearRepository.findById(c.getAcademicYearId()).orElseGet(null);
            LocalDate closureDate = ac.getClosureDate();
            LocalDate finalDate = ac.getFinalClosureDate();
            LocalDate currentDate = LocalDate.now();
            boolean canUpdate = currentDate.isBefore(finalDate);
            boolean canDelete = currentDate.isBefore(closureDate);
            c.setCanUpdate(canUpdate);
            c.setCanDelete(canDelete);
            return c;
        }).toList();
    }
    public void deletefile(String file){
        StoreService.deleteFile(file);
    }
    public void UpdateContribution(String id,String name,String description,LocalDateTime submitDate,String accountId,String academicYearId, String facultyId, MultipartFile file, String oldfile){
        System.out.println("Update contribution service Run");
        if(!file.isEmpty()) {
            StoreService.deleteFile(oldfile);
            System.out.println("old file deleted");
        }
        contributionRepository.UpdateContribution(id,name,description,submitDate,accountId,academicYearId,facultyId,file.getOriginalFilename());
        System.out.println("Contribution repository updated");
        StoreService.store(file);
        System.out.println("Store service store file");
    }
    public void UpdateContribution(String id,String name,String description,LocalDateTime submitDate,String accountId,String academicYearId, String facultyId, String oldfile){
        System.out.println("Update contribution service Run");
        contributionRepository.UpdateContribution(id,name,description,submitDate,accountId,academicYearId,facultyId,oldfile);
        System.out.println("Contribution repository updated");
    }
    public void DeleteContribution(String id){
        Contribution con = contributionRepository.ReturnContribution(id);
        deletefile(con.getPath());
        contributionRepository.DeleteContribution(con.getId());
    }

    public List<Contribution> attachingInfor(List<Contribution> lst){
        return  lst.stream().peek(con ->{
            String year = academicYearRepositoryInterface.findById(con.getAcademicYearId()).get().getYearOfAcademic();
            String facultyName = facultyRepository.findById(con.getFacultyId()).get().getName();
            AcademicYear ac = academicYearRepository.findById(con.getAcademicYearId()).orElseGet(null);
            LocalDate closureDate = ac.getClosureDate();
            LocalDate finalDate = ac.getFinalClosureDate();
            LocalDate currentDate = LocalDate.now();
            boolean canUpdate = currentDate.isBefore(finalDate);
            boolean canDelete = currentDate.isBefore(closureDate);
            boolean canDowload = currentDate.isAfter(finalDate);
            con.setCanDelete(canDelete);
            con.setCanUpdate(canUpdate);
            con.setCanDowload(canDowload);
            con.setFaculty(facultyName);
            con.setYear(year);
        }).toList();
    }

}
