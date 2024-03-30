package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Account;
import com.example.comp1640.repository.AcademicYearRepository;
import com.example.comp1640.repository.AccountRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/Academic")
@Controller
public class AcademicYearController 
{
    @Autowired
    AccountRepositoryTest accountRepo;
    @Autowired
    AcademicYearRepository academicYearRepository;

    @Autowired
    private AccountService accountService;

//    @PostMapping("/Hello")
//    public String Create(@RequestParam("id") String id, @RequestParam("name") String name,
//    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
//        re.CreateAcademicYear(id, name, yearOfAcademic, startDate, endDate);
//        System.out.println("AcademicYear Controller Runed");
//        return "redirect:/Academic/View";
//    }

    @PostMapping("/Hello")
    public String Create(@RequestParam("id") String id, @RequestParam("name") String name,
                         @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate, Model model){
        accountService.checkRole("Admin");
        academicYearRepository.CreateAcademicYear(id, name, yearOfAcademic, startDate, endDate);
        System.out.println("AcademicYear Controller Runed");
        return "redirect:/Academic/View";
    }
    @GetMapping("/CreateAcademicYear") 
    public String CreateAcademicYear(){
        accountService.checkRole("Admin");
        return "AcademicYear/CreateAcademic";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Admin");
        System.out.println(id);
        AcademicYear fe = academicYearRepository.ReturnAcademicYear(id);
        model.addAttribute("AcademicYear", fe);
        return "AcademicYear/UpdateAcademic";
    }

    @PostMapping("/Updating")
    public String UpdatePostAcademicYear(@RequestParam("id") String id, @RequestParam("name") String name, 
    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
        accountService.checkRole("Admin");
        academicYearRepository.UpdateAcademicYear(id, name, yearOfAcademic, startDate, endDate);;
        return "redirect:/Academic/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        accountService.checkRoles("Admin","Marketing Manager");
        Account acc = returnAccount();
        List<AcademicYear> AcademicYears = academicYearRepository.ReturnAcademicYears();
        model.addAttribute("Fals",AcademicYears);
        model.addAttribute("acc",acc);
        return "AcademicYear/ViewAcademic";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        accountService.checkRole("Admin");
        academicYearRepository.DeleteAcademicYear(id);
        return "redirect:/Academic/View";
    }
    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        return accounts;
    }
}