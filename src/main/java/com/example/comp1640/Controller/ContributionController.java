package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.Service.MailService;
import com.example.comp1640.Zip.DownloadService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    ContributionRepositoryInterface contributionRepositoryInterface;

    @Autowired
    MailService mailService;
    @Autowired
    DownloadService downloadService;

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
    @RequestParam(value = "status") int status, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId,
    @RequestParam("facultyId") String facultyId,
    @RequestParam("file") MultipartFile path,
    @RequestParam("oldfile")String oldfile,Model model){
        accountService.checkRole("Student");
        LocalDateTime sub = LocalDateTime.now();
        System.out.println("Updating Contribution controller");
        service.UpdateContribution(id, name, description, sub, status, accountId, academicYearId, facultyId, path, oldfile);
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = acc.get();

        return "redirect:/student/" + accounts.getId();
    }

    @GetMapping("/View")
    public String View(Model model){
        accountService.checkRoles("Marketing Manager","Admin");
        Account account = returnAccount();
        account = accountService.getOne(account.getId());
        List<Faculty> faculties = facultyRepo.ReturnFaculties();
        List<Contribution> contris = service.ReturnForMarketingManager();
        List<Contribution> filledContri = contris.stream()
                .filter(contribution -> contribution.getStatus() != 0 && contribution.getStatus() != 2)
                .collect(Collectors.toList());
        boolean dateCheck =  checkDate(LocalDate.now(),account.getAcademicYear());
        model.addAttribute("acc",account);
        model.addAttribute("cons",filledContri);
        model.addAttribute("faculties",faculties);
        model.addAttribute("dateCheck", dateCheck);
        return "Contribution/ViewContribution";
    }

//    List<Contribution> FilteredList = cons.stream()
//            .filter(con -> Objects.equals(con.getAccountId(), id))
//            .collect(Collectors.toList());


    // @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    // public String CreateFeedBack(@PathVariable String id, Model model) {
    //     Contribution fe = re.ReturnContribution(id);
    //     model.addAttribute("con", fe);
    //     return "Contribution/UpdateContribution";
    // }

    // Delete for Student
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
    //DeleteForAdmin
    @PostMapping("/DeleteForAdmin")
    public String DeleteForAdmin(@RequestParam("id") String id){
        service.DeleteContribution(id);
        return "redirect:/Contribution/View" ;
    }

    @PostMapping("/publicupdate")
    public String Public(@RequestParam("id")String id,@RequestParam(value = "status") int status){
        accountService.checkRoles("Marketing Coordinator","Marketing Manager");
        re.SetPublic(id,status);
        String studentId = re.ReturnContribution(id).getAccountId();
        System.out.println(status);
        return "redirect:/student/" + studentId ;
    }
    @GetMapping("/set/{id}")
    public String set(@PathVariable("id")String id, Model model){
        accountService.checkRole("Marketing Coordinator");
        Account account = returnAccount();
        Contribution con = re.ReturnContribution(id);
        model.addAttribute("con",con);
        model.addAttribute("account",account);
        return "Contribution/SetPublic";
    }
    @GetMapping("/SetPublic/{id}")
    public String setpublic(@PathVariable String id){
        re.SetPublic(id,3);
        return "redirect:/Contribution/View";
    }
    @GetMapping("/downloadmethod")
    public void DownloadMethod(HttpServletResponse   response, @RequestParam("selectedFiles") List<String> list){
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


    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        return accounts;
    }

    public boolean checkDate(LocalDate currentDate, LocalDate deadline){
        if(currentDate.isBefore(deadline)){
            return true;
        }
        return false;
    }
}