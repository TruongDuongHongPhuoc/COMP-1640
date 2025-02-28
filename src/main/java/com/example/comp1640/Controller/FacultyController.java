package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AcademicYearRepository;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.FalcultyRepository;
import com.example.comp1640.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class FacultyController {
    @Autowired
    FalcultyRepository re;

    @Autowired
    AccountRepositoryTest accountRepo;

    @Autowired
    AcademicYearRepository acaRepo;

    @Autowired
    private AccountService accountService;

    @PostMapping("/Hello")
    public String CreateFaculty(@RequestParam("name") String name,

            Model model) {
        accountService.checkRole("Admin");
        String academicYear = "ad721ba6-fe2d-475e-981a-d9376bfcb094";
        String id = UUID.randomUUID().toString();
        String description = "Nothing to descript";
        re.CreateFalcuty(id, name, description, academicYear);
        return "redirect:/view";
    }

    @GetMapping("/CreateFalcuty")
    public String CreatFul(Model model) {
        Account account = returnAccount();
        accountService.checkRole("Admin");
        List<AcademicYear> aca = acaRepo.ReturnAcademicYears();
        model.addAttribute("aca1", aca);
        model.addAttribute("acc",account);
        return "Faculty/CreateFaculty";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
        // System.out.println(id);
        Account account = returnAccount();
        accountService.checkRole("Admin");
        Faculty fe = re.ReturnFaculty(id);
        Map<String, String> dataToToken = new HashMap<>();
        dataToToken.put("id", fe.getId());
        String dataToken = JwtUtils.generateToken(dataToToken);

        model.addAttribute("facultyid",dataToken);
        model.addAttribute("acc",account);
        model.addAttribute("faculty", fe);
        return "Faculty/UpdateFaculty";
    }

    @PostMapping("/Updating")
    public String UpdatePostFaculty(@RequestParam("name") String name, @RequestParam("id") String id,

            Model model) {
        accountService.checkRole("Admin");
        Map<String, Object> dataFromToken = JwtUtils.decodeToken(id);
        String decodedId = dataFromToken.get("id").toString();
        String academicYear = "ad721ba6-fe2d-475e-981a-d9376bfcb094";
        String description = "Nothing to descript";
        re.UpdateFalcuty(decodedId, name, description, academicYear);
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String View(Model model) {
        Account acc = returnAccount();
        accountService.checkRole("Admin");
        List<AcademicYear> academicYears = acaRepo.ReturnAcademicYears();
        List<Faculty> Faculties = re.ReturnFaculties();
        model.addAttribute("Fals", Faculties);
        model.addAttribute("acc",acc);
        model.addAttribute("aca",academicYears);
        return "Faculty/ViewFacutlty";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        accountService.checkRole("Admin");
        re.DeleteFal(id);
        return "redirect:/view";
    }

    @GetMapping("/DeleteGet/{id}")
    public String DeleteGet(@PathVariable("id") String id) {
        accountService.checkRole("Admin");
        re.DeleteFal(id);
        return "redirect:/view";
    }

    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        return accounts;
    }
}