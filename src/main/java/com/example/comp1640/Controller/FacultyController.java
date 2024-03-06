package com.example.comp1640.Controller;

import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FacultyController {
    @Autowired
    FalcultyRepository re;

    @GetMapping("/add")
    public String addFalcuty(){
        re.Create();
        return "Admin";
    }
    @GetMapping("/Update")
    public String Update(){
//        re.UpdateFalcuty();
        return "Admin";
    }
}
