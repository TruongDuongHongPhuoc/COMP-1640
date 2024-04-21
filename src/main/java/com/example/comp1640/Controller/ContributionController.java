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

import com.example.comp1640.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Contribution")
public class ContributionController {
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
        List<Faculty> faculties = facultyRepo.ReturnFaculties();
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account account = accountService.getOne(acc.get().getId());
        List<AcademicYear> listAcademicYear = academicYearRepositoryInterface.findAll();
        List<AcademicYear> tempListAcademicYear = new ArrayList<>();
        for (AcademicYear a : listAcademicYear) {
            if (a.getClosureDate().isAfter(LocalDate.now())) {
                tempListAcademicYear.add(a);
            }
        }
        Contribution contribution = new Contribution();
        model.addAttribute("contribution", contribution);
        model.addAttribute("acc", account);
        model.addAttribute("acaYear", tempListAcademicYear);
        model.addAttribute("fals", faculties);
        return "Contribution/CreateContribution";
    }

    @PostMapping("/Hello")
    public String Create(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("academic") String academic,
                         @RequestParam("file") MultipartFile file, @RequestParam("image") MultipartFile image, Model model) throws IOException {
        accountService.checkRole("Student");
        if (!file.getOriginalFilename().matches("^.*\\.(docx|doc)$")){
            throw new IllegalArgumentException("The file must be document");
        }
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account account = accountService.getOne(acc.get().getId());

        Contribution contribution = new Contribution();
        contribution.setName(name);
        contribution.setDescription(description);
        String id = UUID.randomUUID().toString();
        LocalDate submitDate = LocalDate.now();
        contribution.setId(id);
        contribution.setAcademicYearId(academic);
        contribution.setSubmitDate(submitDate);
        contribution.setStatus(0);
        contribution.setFacultyId(account.getFacultyId());
        contribution.setAccountId(account.getId());
        contribution.setPath(file.getOriginalFilename());
        contribution.setImage(image.getOriginalFilename());
        service.storeFile(image);
        service.saveImage(file);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatted = contribution.getSubmitDate().plusDays(14).format(formatter);
        List<Account> listAccount = accountRepo.findAll();
        for (Account a : listAccount) {
            if (a.getRoleId().equals("3") && a.getFacultyId().equals(contribution.getFacultyId())) {
                mailService.SendEmail(a.getMail(),
                        "Feedback",
                        "The student with email: " + account.getMail() + " just contributed and need to receive feedback, deadline is: " + formatted);
            }
        }
        Contribution saveContribution = contributionRepositoryInterface.save(contribution);
        return "redirect:/student/" + account.getId();
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String updateContribution(@PathVariable String id, Model model) {
        accountService.checkRole("Student");
        Contribution fe = re.ReturnContribution(id);
        Map<String, String> dataToToken = new HashMap<>();
        dataToToken.put("id", fe.getId());
        dataToToken.put("oldfile", fe.getPath());
        dataToToken.put("accountId", fe.getAccountId());
        dataToToken.put("facultyId", fe.getFacultyId());
        dataToToken.put("academicYearId",fe.getAcademicYearId());
        String dataToken = JwtUtils.generateToken(dataToToken);
        model.addAttribute("con", fe);
        model.addAttribute("token", dataToken);
        Account account = returnAccount();
        model.addAttribute("acc",account);
        return "Contribution/UpdateContribution";
    }


    @PostMapping("/Updating")
    public String UpdatePostContribution(
            @RequestParam("name") String name, @RequestParam("description") String description,
            @RequestParam("token") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam("image") MultipartFile image
            , Model model) throws IOException {
        Map<String, Object> dataFromToken = JwtUtils.decodeToken(token);
        String id = dataFromToken.get("id").toString();
        if (!file.getOriginalFilename().matches("^.*\\.(docx|doc)$")){
            throw new IllegalArgumentException("The file must be document");
        }

        String academicId = dataFromToken.get("academicYearId").toString();
        String accountId = dataFromToken.get("accountId").toString();
        String facultyId = dataFromToken.get("facultyId").toString();
        String oldPath = dataFromToken.get("oldfile").toString();
        if(!file.isEmpty()) {
            service.deleteContribution(id, name, description, LocalDateTime.now(), accountId, academicId, facultyId, file, oldPath, image);
        }
        else {
            service.updateContribution(id,name,description,LocalDateTime.now(),accountId,academicId,facultyId,oldPath, image);
        }
        Account account = returnAccount();

        if(account.getRoleName().equals("Student")) {

            return "redirect:/student/" + account.getId();
        }else {
            return "redirect:/Contribution/View";
        }
        }

    @GetMapping("/View")
    public String View(Model model) {
        accountService.checkRole("Marketing Manager");
        Account account = returnAccount();
        List<AcademicYear> academicYears = acaRepo.ReturnAcademicYears();
        account = accountService.getOne(account.getId());
        List<Faculty> faculties = facultyRepo.ReturnFaculties();
        List<Contribution> contris = service.ReturnForMarketingManager();
        List<Contribution> filledContri = contris.stream()
                .filter(contribution -> contribution.getStatus() != 0 && contribution.getStatus() != 2)
                .collect(Collectors.toList());
        model.addAttribute("acc", account);
        model.addAttribute("cons", filledContri);
        model.addAttribute
        ("faculties", faculties);
        model.addAttribute("academicYears",academicYears);
        return "Contribution/ViewContribution";
    }


    // Delete for Student
    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        // accountService.checkRole("Student");
        return getString(id);
    }
    @GetMapping("/DeleteGet/{id}")
    public String DeleteGet(@PathVariable("id") String id) {
        // accountService.checkRole("Student");
        return getString(id);
    }

    private String getString(@PathVariable("id") String id) {
        Contribution con = re.ReturnContribution(id);
        service.deletefile(con.getPath());
        service.DeleteContribution(id);
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Account accounts = returnAccount();
        return "redirect:/student/" + accounts.getId();
    }

    //DeleteForAdmin
    // @GetMapping("/DeleteForAdmin/{id}")
    // public String DeleteForAdmin(@PathVariable("id") String id) {
    //     service.DeleteContribution(id);
    //     return "redirect:/Contribution/View";
    // }

    @PostMapping("/publicupdate")
    public String Public(@RequestParam("conId") String token, @RequestParam(value = "status") int status) {
        accountService.checkRoles("Marketing Coordinator", "Marketing Manager");
        // Decode the token
        Map<String, Object> dataFromToken = JwtUtils.decodeToken(token);

            String id = dataFromToken.get("id").toString();

            re.SetPublic(id, status);
            String studentId = re.ReturnContribution(id).getAccountId();
            System.out.println(status);
            return "redirect:/student/" + studentId;
        }


    @GetMapping("/set/{id}")
    public String set(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Marketing Coordinator");
        Account account = returnAccount();
        Contribution con = re.ReturnContribution(id);
        //send id to view
        Map<String,String> dataToToken = new HashMap<>();
        dataToToken.put("id",con.getId());
        String dataToken = JwtUtils.generateToken(dataToToken);
        model.addAttribute("conId",dataToken);

        model.addAttribute("con", con);
        model.addAttribute("account", account);
        return "Contribution/SetPublic";
    }

    @GetMapping("/SetPublic/{id}")
    public String setpublic(@PathVariable String id) {
        re.SetPublic(id, 3);
        return "redirect:/Contribution/View";
    }

    @GetMapping("/downloadmethod")
    public void DownloadMethod(HttpServletResponse response, @RequestParam("selectedFiles") List<String> list) {
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;
        List<String> DownloadList = new ArrayList<>();
        for (String con : list) {
            String downitem = directoryPath + con;
            DownloadList.add(downitem);
            System.out.println(downitem);
        }
        downloadService.downloadZipFile(response, DownloadList);
    }


    public Account returnAccount() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account account = accountService.getOne(acc.get().getId());
        return account;
    }

}