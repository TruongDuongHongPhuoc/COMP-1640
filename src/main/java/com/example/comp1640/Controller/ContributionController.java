package com.example.comp1640.Controller;

import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContributionController 
{
    @Autowired
    ContributionRepository re;

    @PostMapping("/Hello")
    public String Create(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        re.CreateContribution(id, name, year, null, null, group, group);
        return "ViewFacutlty";
    }
    @GetMapping("/CreateContribution") 
    public String CreatFul(){
        return "Contribution/CreateContribution";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String update(@RequestParam("id") String id, Model model) {
//        System.out.println(id);
        Contribution fe = re.ReturnContribution(id);
        model.addAttribute("Contribution", fe);
        return "Contribution/UpdateContribution";
    }
    @PostMapping("/Updating")
    public String UpdatePostContribution(@RequestParam("name") String name,@RequestParam("id") String id
            ,@RequestParam("group") int group,@RequestParam("Year") String year, Model model){
        System.out.println(name);
        System.out.println(id);
        System.out.println(group);
        System.out.println(year);
        re.UpdateContribution(id, name, year, null, null, group, group);;
        return "redirect:/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Contribution> Faculties = re.ReturnFaculties();
        model.addAttribute("Fals",Faculties);
        return "Contribution/ViewFacutlty";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteContribution(id);
        return "redirect:/View";
    }
}