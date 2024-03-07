package com.example.comp1640.Controller;

import com.example.comp1640.model.Faculty;
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

    @PostMapping("/Hello")
    public String CreateFaculty(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        re.CreateFalcuty(id,name,group,year);
        return "ViewFacutlty";
    }
    @GetMapping("/CreateFalcuty") 
    public String CreatFul(){
        return "Faculty/CreateFaculty";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String update(@RequestParam("id") String id, Model model) {
//        System.out.println(id);
        Faculty fe = re.ReturnFaculty(id);
        model.addAttribute("faculty", fe);
        return "Faculty/UpdateFaculty";
    }
    @PostMapping("/Updating")
    public String UpdatePostFaculty(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        System.out.println(name);
        System.out.println(id);
        System.out.println(group);
        System.out.println(year);
        re.UpdateFalcuty(id,name,group,year);
        return "redirect:/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Faculty> Faculties = re.ReturnFaculties();
        model.addAttribute("Fals",Faculties);
        return "Faculty/ViewFacutlty";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteFal(id);
        return "redirect:/View";
    }
}