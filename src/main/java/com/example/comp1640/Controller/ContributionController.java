package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;

import com.example.comp1640.Zip.DownloadService;

import com.example.comp1640.Service.MailService;

import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.*;


import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.awt.*;
import java.io.File;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/Contribution")
public class ContributionController 
{
    @Autowired
    ContributionRepository re;

    @Autowired
    AccountRepositoryTest accountRepo;

    @Autowired
    FalcultyRepository facultyRepo;
    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    AcademicYearRepository acaRepo;

    @Autowired
    ContributionService service;

    @Autowired
    AcademicYearRepositoryInterface academicYearRepositoryInterface;

    @Autowired
    private AccountService accountService;
    @Autowired
    private DownloadService downloadService;

    @Autowired
    ContributionRepositoryInterface contributionRepositoryInterface;

    @Autowired
    MailService mailService;

    @GetMapping("/Createcontribution") // Corrected mapping without the trailing slash
    public String create(Model model) {
        accountService.checkRole("Student");

        List<AcademicYear> academicYears = acaRepo.ReturnAcademicYears();
        List<Faculty> faculties = facultyRepo.ReturnFaculties();
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        System.out.println(accounts.getFacultyId());
        Contribution contribution = new Contribution();
        model.addAttribute("contribution", contribution);
        model.addAttribute("acc", accounts);
        model.addAttribute("acaYear", academicYears);
        model.addAttribute("fals", faculties);
        return "Contribution/CreateContribution";
    }
    @PostMapping("/Hello")
    public String Create(@Valid @ModelAttribute("contribution") Contribution contribution,
                         @RequestParam("file")MultipartFile file, BindingResult result, Model model){
        accountService.checkRole("Student");

        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account account = accountService.getOne(acc.get().getId());

        String id = UUID.randomUUID().toString();
        LocalDate submitDate = LocalDate.now();
        contribution.setId(id);
        contribution.setSubmitDate(submitDate);
        contribution.setAcademicYearId(facultyRepository.findById(account.getFacultyId()).get().getAcademicYear());
        contribution.setStatus(0);
        contribution.setFacultyId(account.getFacultyId());
        contribution.setAccountId(account.getId());
        String z = contribution.getFacultyId();
        contribution.setPath(file.getOriginalFilename());
        service.storeFile(file);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatted = contribution.getSubmitDate().plusDays(14).format(formatter);
        List<Account> listAccount = accountRepo.findAll();
        for(Account a : listAccount){
            if (a.getRoleId().equals("3") && a.getFacultyId().equals(contribution.getFacultyId())) {
                mailService.SendEmail(a.getMail(),
                        "Feedback",
                        "The student with email: " + account.getMail() +" just contributed and need to receive feedback, deadline is: "+ formatted);
            }
        }
        Contribution saveContribution = contributionRepositoryInterface.save(contribution);
        return "redirect:/student/" + account.getId();
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String updateContribution(@PathVariable String id, Model model) {
        accountService.checkRole("Student");
        Contribution fe = re.ReturnContribution(id);
        model.addAttribute("con", fe);
        return "Contribution/UpdateContribution";
    }
//    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
//    public String update(@PathVariable("id") String id, Model model) {
//        System.out.println(id);
//        AcademicYear fe = re.ReturnAcademicYear(id);
//        model.addAttribute("AcademicYear", fe);
//        return "AcademicYear/UpdateAcademic";
//    }
    @PostMapping("/Updating")
    public String UpdatePostContribution(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description,
    @RequestParam("submitDate") LocalDateTime submitDate,
    @RequestParam(value = "status") int status, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId,
    @RequestParam("facultyId") String facultyId,
    @RequestParam("file") MultipartFile path,
    @RequestParam("oldfile")String oldfile,Model model){
        accountService.checkRole("Student");
        System.out.println("Updating Contribution controller");
        service.UpdateContribution(id, name, description, submitDate, status, accountId, academicYearId, facultyId, path, oldfile);
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = acc.get();

        return "redirect:/student/" + accounts.getId();
    }
    @GetMapping("/DownloadAllForManager")
    public void DownloadForManager(HttpServletResponse response){
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;
        List<String> DownloadList = new ArrayList<>();
        List<Contribution> contris = service.ReturnForMarketingManager();
        List<Contribution> filledContri = contris.stream()
                .filter(contribution -> contribution.getStatus() != 0 && contribution.getStatus() != 2)
                .collect(Collectors.toList());
        for (Contribution con : filledContri){
            String downitem = directoryPath + con.getPath();
            DownloadList.add(downitem);
            System.out.println(downitem);
        }
        downloadService.downloadZipFile(response, DownloadList);
    }
//    @PostMapping("/testDownloadPot")
//    public void Downloadss(HttpServletResponse response){
//        String path = System.getProperty("user.dir");
//        String subPath = File.separator + "upload-dir" + File.separator;
//        String directoryPath = path + subPath + "BlackDoc.docx";
//        List<String> lit = new ArrayList<>();
//        lit.add(directoryPath);
//        System.out.println("Downlaod Service run");
//        downloadService.downloadZipFile(response,lit);
//    }
    @GetMapping("/downloadmethod")
    public void DownloadMethod(HttpServletResponse response,@RequestParam("selectedFiles") List<String> list){
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;
        List<String> DownloadList = new ArrayList<>();

        for (String con : list){
            String downitem = directoryPath + con;
            DownloadList.add(downitem);
            System.out.println(downitem);
        }
        downloadService.downloadZipFile(response, DownloadList);
    }

    @GetMapping("/setpublicCon/{id}")
    public String SetpublicCon(@PathVariable String id){
        re.SetPublic(id,3);
        Contribution con = re.ReturnContribution(id);
        System.out.println(con.getStatus());
        return "redirect:/Contribution/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        accountService.checkRole("Marketing Manager");
        List<Contribution> contris = service.ReturnForMarketingManager();
        List<Contribution> filledContri = contris.stream()
                .filter(contribution -> contribution.getStatus() != 0 && contribution.getStatus() != 2)
                .collect(Collectors.toList());
        model.addAttribute("cons",filledContri);
        return "Contribution/ViewContribution";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id, @RequestParam("file") String file){
        // accountService.checkRole("Student");
        re.DeleteContribution(id);
        service.deletefile(file);
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = acc.get();

        return "redirect:/student/" + accounts.getId();
    }

    @PostMapping("/publicupdate")
    public String Public(@RequestParam("id")String id,@RequestParam(value = "status") int status){
        accountService.checkRoles("Marketing Coordinator","Marketing Manager");
        re.SetPublic(id,status);
        Contribution con = re.ReturnContribution(id);
        String accid = con.getAccountId();
        System.out.println(status);
        return "redirect:/student/" + accid;
    }
    @GetMapping("/set/{id}")
    public String set(@PathVariable("id")String id, Model model){
        Account account = returnAccount();
        accountService.checkRole("Marketing Coordinator");
        Contribution con = re.ReturnContribution(id);
        model.addAttribute("con",con);
        model.addAttribute("account",account);
        return "Contribution/SetPublic";
    }


    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        return accounts;
    }

}