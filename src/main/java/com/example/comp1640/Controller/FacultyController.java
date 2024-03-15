package com.example.comp1640.Controller;

import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AcademicYearRepository;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FacultyController 
{
    @Autowired
    FalcultyRepository re;

    @Autowired
    AcademicYearRepository acaRepo;

    @PostMapping("/Hello")
    public String CreateFaculty(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("description") String description,@RequestParam("academicYear") String academicYear, Model model){
        re.CreateFalcuty(id, name, description, academicYear);
        return "redirect:/view";
    }


    @GetMapping("/CreateFalcuty")
    public String CreatFul(Model model){
        List<AcademicYear> aca = acaRepo.ReturnAcademicYears();
        model.addAttribute("aca1", aca);
        return "Faculty/CreateFaculty";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
//        System.out.println(id);
        Faculty fe = re.ReturnFaculty(id);
        model.addAttribute("faculty", fe);
        return "Faculty/UpdateFaculty";
    }

    @PostMapping("/Updating")
    public String UpdatePostFaculty(@RequestParam("name") String name,@RequestParam("id") String id
    ,@RequestParam("description") String description,@RequestParam("academicYear") String academicYear, Model model){
        re.UpdateFalcuty(id,name,description,academicYear);
        return "redirect:/view";
    }
    
    @GetMapping("/view")
    public String View(Model model){
        List<Faculty> Faculties = re.ReturnFaculties();
        model.addAttribute("Fals",Faculties);
        return "Faculty/ViewFacutlty";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteFal(id);
        return "redirect:/view";
    }
}