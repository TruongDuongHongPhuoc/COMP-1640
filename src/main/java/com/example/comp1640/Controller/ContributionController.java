package com.example.comp1640.Controller;

import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AcademicYearRepository;
import com.example.comp1640.repository.AccountRepository;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FalcultyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
    AcademicYearRepository acaRepo;

    @Autowired
    ContributionService service;

    @Autowired
    AccountRepositoryTest accountRepoTest;

    @GetMapping("/CreateContribution") // Corrected mapping without the trailing slash
    public String create(Model model) {
        List<AcademicYear> academicYears = acaRepo.ReturnAcademicYears();
        List<Faculty> faculties = facultyRepo.ReturnFaculties();
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  
        Optional<Account> acc = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        System.out.println(accounts);
        model.addAttribute("acc", accounts);
        model.addAttribute("acaYear", academicYears);
        model.addAttribute("fals", faculties);
        return "Contribution/CreateContribution";
    }
    @PostMapping("/Hello")
    public String Create(@RequestParam("name") String name,
                         @RequestParam("description") String description,
                         @RequestParam("submitDate") String submitDate,
                         @RequestParam(value = "status", defaultValue = "0") int status,
                         @RequestParam("accountId") String accountId,
                         @RequestParam("academicYearId") String academicYearId,
                         @RequestParam("facultyId") String facultyId,
                         @RequestParam("file")MultipartFile file, Model model){
        String id = UUID.randomUUID().toString();
        service.CreateContribution(id, name, description, submitDate, status, accountId, academicYearId, facultyId, file);;
        return "redirect:/Contribution/View";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String updateContribution(@PathVariable String id, Model model) {
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
    @RequestParam("submitDate") String submitDate,
    @RequestParam(value = "status") int status, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId,
    @RequestParam("facultyId") String facultyId,
    @RequestParam("file") MultipartFile path,
    @RequestParam("oldfile")String oldfile,Model model){
        service.UpdateContribution(id, name, description, submitDate, status, accountId, academicYearId, facultyId, path, oldfile);
        return "redirect:/Contribution/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Contribution> contris = re.ReturnContributions();
        model.addAttribute("cons",contris);
        return "Contribution/ViewContribution";
    }

    // @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    // public String CreateFeedBack(@PathVariable String id, Model model) {
    //     Contribution fe = re.ReturnContribution(id);
    //     model.addAttribute("con", fe);
    //     return "Contribution/UpdateContribution";
    // }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id, @RequestParam("file") String file){
        re.DeleteContribution(id);
        service.deletefile(file);
        return "redirect:/Contribution/View";
    }

    @PostMapping("/publicupdate")
    public String Public(@RequestParam("id")String id,@RequestParam(value = "status") int status){
        re.SetPublic(id,status);
        System.out.println(status);
        return "redirect:/Contribution/View";
    }
    @GetMapping("/set/{id}")
    public String set(@PathVariable("id")String id, Model model){
        Contribution con = re.ReturnContribution(id);
        model.addAttribute("con",con);
        return "Contribution/SetPublic";
    }
}