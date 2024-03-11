package com.example.comp1640.Controller;

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
        re.CreateAcademicYear(id, name, yearOfAcademic, startDate, endDate);
        System.out.println("AcademicYear Controller Runed");
        return "redirect:/Academic/View";
    }
    @GetMapping("/CreateAcademicYear") 
    public String CreateAcademicYear(){
        return "AcademicYear/CreateAcademic";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String update(@RequestParam("id") String id, Model model) {
        AcademicYear fe = re.ReturnAcademicYear(id);
        model.addAttribute("AcademicYear", fe);
        return "AcademicYear/UpdateAcademic";
    }

    @PostMapping("/Updating")
    public String UpdatePostAcademicYear(@RequestParam("id") String id, @RequestParam("name") String name, 
    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
        re.UpdateAcademicYear(id, name, yearOfAcademic, startDate, endDate);;
        return "redirect:/Academic/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<AcademicYear> AcademicYears = re.ReturnAcademicYears();
        model.addAttribute("Fals",AcademicYears);
        return "AcademicYear/ViewAcademic";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteAcademicYear(id);
        return "redirect:/Academic/View";
    }
}