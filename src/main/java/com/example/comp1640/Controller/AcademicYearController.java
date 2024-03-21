package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.repository.AcademicYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@RequestMapping("/Academic")
@Controller
public class AcademicYearController 
{
    @Autowired
    AcademicYearRepository re;

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
    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
        accountService.checkRole("Admin");
        re.CreateAcademicYear(id, name, yearOfAcademic, startDate, endDate);
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
        AcademicYear fe = re.ReturnAcademicYear(id);
        model.addAttribute("AcademicYear", fe);
        return "AcademicYear/UpdateAcademic";
    }

    @PostMapping("/Updating")
    public String UpdatePostAcademicYear(@RequestParam("id") String id, @RequestParam("name") String name, 
    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
        accountService.checkRole("Admin");
        re.UpdateAcademicYear(id, name, yearOfAcademic, startDate, endDate);;
        return "redirect:/Academic/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        accountService.checkRole("Admin");
        List<AcademicYear> AcademicYears = re.ReturnAcademicYears();
        model.addAttribute("Fals",AcademicYears);
        return "AcademicYear/ViewAcademic";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        accountService.checkRole("Admin");
        re.DeleteAcademicYear(id);
        return "redirect:/Academic/View";
    }
}