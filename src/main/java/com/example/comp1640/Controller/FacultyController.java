package com.example.comp1640.Controller;

import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FacultyController {
    @Autowired
    FalcultyRepository re;

    @GetMapping("/add")
    public String addFalcuty(){
//        re.CreateFalcuty();
        return "Admin";
    }
    @PostMapping("/Hello")
    public String Concaheo(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        re.CreateFalcuty(id,name,group,year);
        return "Admin";
    }
    @GetMapping("/CreateFalcuty") 
    public String CreatFul(){
        return "CreateFalculty";
    }

    @GetMapping("/Update")
    public String Update(){
        re.UpdateFalcuty("a","bb",3,"2024-2-4");
        return "Admin";
    }
    @GetMapping("/View")
    public String View(Model model){
        List<Faculty> Fals = re.ReturnFalcuty();
        model.addAttribute("Fals",Fals);
        return "ViewFalcutlty";
    }
}
