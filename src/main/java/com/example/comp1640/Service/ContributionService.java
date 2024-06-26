package com.example.comp1640.Service;

import com.example.comp1640.Store.FileSystemStorageService;
import com.example.comp1640.Store.FileUploadController;
import com.example.comp1640.model.*;
import com.example.comp1640.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

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
    @Autowired
    AccountService accountService;
    @Autowired
    ContributionRepositoryInterface contributionRepositoryInterface;

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
        return attachingInfor(filteredList);
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
    public void deleteContribution(String id, String name, String description, LocalDateTime submitDate, String accountId, String academicYearId, String facultyId, MultipartFile file, String oldfile, MultipartFile image) throws IOException {
        if(!file.isEmpty()) {
            StoreService.deleteFile(oldfile);
        }
        updateContribution(id,name,description,submitDate,accountId,academicYearId,facultyId,oldfile,image);
        StoreService.store(file);
        saveImage(image);
    }
    public void updateContribution(String id, String name, String description, LocalDateTime submitDate, String accountId, String academicYearId, String facultyId, String oldfile, MultipartFile image){
//        contributionRepository.UpdateContribution(id,name,description,submitDate,accountId,academicYearId,facultyId,oldfile);
        Contribution con = contributionRepositoryInterface.findById(id).get();
        con.setName(name);
        con.setDescription(description);
        con.setAcademicYearId(academicYearId);
        con.setFacultyId(facultyId);
        con.setImage(image.getOriginalFilename());
        contributionRepositoryInterface.save(con);
    }
    public void DeleteContribution(String id){
        Contribution con = contributionRepository.ReturnContribution(id);
        deletefile(con.getPath());
        contributionRepository.DeleteContribution(con.getId());
        List<Feedback> feedbacksToDelete = feedbackRepository.ReturnFeedBacksWithContributionId(id);
        for(Feedback fe : feedbacksToDelete){
            feedbackRepository.DeleteFeedback(fe.getId());
        }
    }

    public List<Contribution> attachingInfor(List<Contribution> lst){
        return  lst.stream().peek(con ->{
            String year = academicYearRepositoryInterface.findById(con.getAcademicYearId()).get().getYearOfAcademic();
            String facultyName = facultyRepository.findById(con.getFacultyId()).get().getName();
            AcademicYear ac = academicYearRepository.findById(con.getAcademicYearId()).orElseGet(null);
            Account account = accountService.getOne(con.getAccountId());
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
            con.setAuthor(account.getMail());
            con.setYear(year);
        }).toList();
    }

    public Contribution attachingInfor(String id){
        Contribution con = contributionRepository.ReturnContribution(id);
        String year = academicYearRepositoryInterface.findById(con.getAcademicYearId()).get().getYearOfAcademic();
        String facultyName = facultyRepository.findById(con.getFacultyId()).get().getName();
        AcademicYear ac = academicYearRepository.findById(con.getAcademicYearId()).orElseGet(null);
        Account account = accountService.getOne(con.getAccountId());
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
        con.setAuthor(account.getMail());
        con.setYear(year);

        return con;
    }

    public void saveImage(MultipartFile file) throws IOException{
        try {
            String fileName = file.getOriginalFilename();
            String filePath = "src/main/resources/static/images/" + fileName;
            File imageFileOnDisk = new File(filePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream((imageFileOnDisk)));
            stream.write(file.getBytes());
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
