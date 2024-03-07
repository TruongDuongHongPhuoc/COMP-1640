package com.example.comp1640.Controller;

import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Contribution")
public class ContributionController 
{
    @Autowired
    ContributionRepository re;

    @PostMapping("/Hello")
    public String Create(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("typeOfFile") String typeOfFile, 
    @RequestParam("submitDate") Date submitDate, @RequestParam("isPublic") Boolean isPublic, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId, Model model){
        re.CreateContribution(id, name, typeOfFile, submitDate, isPublic, accountId, academicYearId);
        return "ViewContribution";
    }
    @GetMapping("/CreateContribution") 
    public String CreatFul(){
        return "Contribution/CreateContribution";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String update(@RequestParam("id") String id, Model model) {
        Contribution fe = re.ReturnContribution(id);
        model.addAttribute("Contribution", fe);
        return "Contribution/UpdateContribution";
    }
    @PostMapping("/Updating")
    public String UpdatePostContribution(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("typeOfFile") String typeOfFile, 
    @RequestParam("submitDate") Date submitDate, @RequestParam("isPublic") Boolean isPublic, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId, Model model){
        re.UpdateContribution(id, name, typeOfFile, submitDate, isPublic, accountId, academicYearId);;
        return "redirect:/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Contribution> Faculties = re.ReturnContributions();
        model.addAttribute("Fals",Faculties);
        return "Contribution/ViewContribution";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteContribution(id);
        return "redirect:/View";
    }
}