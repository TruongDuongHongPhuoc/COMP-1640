package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Account;
import com.example.comp1640.repository.AcademicYearRepository;
import com.example.comp1640.repository.AcademicYearRepositoryInterface;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequestMapping("/Academic")
@Controller
public class AcademicYearController 
{
    @Autowired
    AccountRepositoryTest accountRepo;
    @Autowired
    AcademicYearRepository academicYearRepository;
    @Autowired
    AcademicYearRepositoryInterface academicYearRepositoryInterface;
    @Autowired
    private AccountService accountService;

    @PostMapping("/Hello")
    public String Create(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate, Model model){
        accountService.checkRole("Admin");
        String id = UUID.randomUUID().toString();
        String yearOfAcademic = genergateAcademicYear(LocalDate.now(), endDate);
        AcademicYear academicYear = new AcademicYear(id, null, yearOfAcademic, startDate, endDate);
        academicYearRepositoryInterface.save(academicYear);
        return "redirect:/Academic/View";
    }

    public String genergateAcademicYear(LocalDate closureDate, LocalDate finalClosureDate){
        String academicYear = closureDate.getYear() + " - " + finalClosureDate.getYear();
        return academicYear;
    }
    @GetMapping("/CreateAcademicYear") 
    public String CreateAcademicYear(Model model){
        Account acc = returnAccount();
        accountService.checkRole("Admin");
        model.addAttribute("acc",acc);
        return "AcademicYear/CreateAcademic";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Admin");
        Account acc = returnAccount();
        System.out.println(id);
        AcademicYear fe = academicYearRepository.ReturnAcademicYear(id);
        Map<String, String> dataToToken = new HashMap<>();
        dataToToken.put("id", fe.getId());
        String dataToken = JwtUtils.generateToken(dataToToken);

        model.addAttribute("AcademicYearId",dataToken);
        model.addAttribute("AcademicYear", fe);
        model.addAttribute("acc",acc);
        return "AcademicYear/UpdateAcademic";
    }

    @PostMapping("/Updating")
    public String UpdatePostAcademicYear(@RequestParam("id") String id,@RequestParam("closureDate") LocalDate closureDate, @RequestParam("finalClosureDate") LocalDate finalClosureDate, Model model){
        accountService.checkRole("Admin");
        Map<String, Object> dataFromToken = JwtUtils.decodeToken(id);
        String decodedid = dataFromToken.get("id").toString();
        Optional<AcademicYear> optionalAcademicYear = academicYearRepositoryInterface.findById(id);
        AcademicYear academicYear = optionalAcademicYear.get();
        academicYear.setClosureDate(closureDate);
        academicYear.setFinalClosureDate(finalClosureDate);
        academicYearRepositoryInterface.save(academicYear);
        return "redirect:/Academic/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        accountService.checkRole("Admin");
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
    @GetMapping("/DeleteGet/{id}")
    public String DeleteGet(@PathVariable("id") String id) {
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