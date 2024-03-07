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
    public String Create(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        re.CreateAcademicYear(id, name, year, null, null, group, group);;
        return "ViewFacutlty";
    }
    @GetMapping("/CreateAcademicYear") 
    public String CreatFul(){
        return "AcademicYear/CreateAcademicYear";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String update(@RequestParam("id") String id, Model model) {
        AcademicYear fe = re.ReturnAcademicYear(id);
        model.addAttribute("AcademicYear", fe);
        return "AcademicYear/UpdateAcademicYear";
    }
    @PostMapping("/Updating")
    public String UpdatePostAcademicYear(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        System.out.println(name);
        System.out.println(id);
        System.out.println(group);
        System.out.println(year);
        re.UpdateAcademicYear(id, name, year, null, null, group, group);;
        return "redirect:/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<AcademicYear> Faculties = re.ReturnFaculties();
        model.addAttribute("Fals",Faculties);
        return "AcademicYear/ViewFacutlty";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteAcademicYear(id);
        return "redirect:/View";
    }
}