package com.example.comp1640.Controller;

import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.Contribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class StudentController {
    @Autowired
    private ContributionService contributionService;
    @GetMapping("/student/{id}")
    public String ViewWork(@PathVariable String id, Model model){
        List<Contribution> cons = contributionService.ReturnAllContribution();
        List<Contribution> FilteredList = cons.stream()
                .filter(con -> Objects.equals(con.getAccountId(), id))
                .collect(Collectors.toList());
        model.addAttribute("cons",FilteredList);
        return "ViewWork";
    }
}
