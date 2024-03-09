package com.example.comp1640.Controller;

import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.repository.AcademicYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Academic")
@Controller
public class AcademicYearController 
{
    @Autowired
    AcademicYearRepository re;

    @PostMapping("/Hello")
    public String Create(@RequestParam("id") String id, @RequestParam("name") String name, 
    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
        re.CreateAcademicYear(id, name, yearOfAcademic, startDate, endDate);;
        return "AcademicYear/ViewAcademicYear";
    }
    
    @GetMapping("/CreateAcademicYear") 
    public String CreateAcademicYear(){
        return "AcademicYear/CreateAcademicYear";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String update(@RequestParam("id") String id, Model model) {
        AcademicYear fe = re.ReturnAcademicYear(id);
        model.addAttribute("AcademicYear", fe);
        return "AcademicYear/UpdateAcademicYear";
    }

    @PostMapping("/Updating")
    public String UpdatePostAcademicYear(@RequestParam("id") String id, @RequestParam("name") String name, 
    @RequestParam("yearOfAcademic") String yearOfAcademic, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model){
        re.UpdateAcademicYear(id, name, yearOfAcademic, startDate, endDate);;
        return "redirect:/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<AcademicYear> AcademicYears = re.ReturnAcademicYears();
        model.addAttribute("Fals",AcademicYears);
        return "AcademicYear/ViewAcademicYear";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteAcademicYear(id);
        return "redirect:/View";
    }
}