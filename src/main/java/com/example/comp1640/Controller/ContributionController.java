package com.example.comp1640.Controller;

import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/Contribution")
public class ContributionController 
{
    @Autowired
    ContributionRepository re;

    @PostMapping("/Hello")
    public String Create(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description,
                         @RequestParam("submitDate") String submitDate, 
                         @RequestParam(value = "approve", defaultValue = "false") Boolean approve, 
                         @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic, @RequestParam("accountId") String accountId,
                         @RequestParam("academicYearId") String academicYearId, Model model){
        Contribution contri = new Contribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId);
        if(contri == null || contri.equals(null)){
            System.out.println("CONTRI IS NULL");
        }
                re.CreateContribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId);
        return "redirect:/Contribution/View";
    }
    @GetMapping("/CreateContribution")
    public String CreatContribution(){
        return "Contribution/CreateContribution";
    }

    @GetMapping("/Update") // Corrected mapping without the trailing slash
    public String updateContribution(@RequestParam("id") String id, Model model) {
        Contribution fe = re.ReturnContribution(id);
        model.addAttribute("con", fe);
        return "Contribution/UpdateContribution";
    }
    @PostMapping("/Updating")
    public String UpdatePostContribution(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description,
    @RequestParam("submitDate") String submitDate, 
    @RequestParam(value = "approve", defaultValue = "false") Boolean approve, 
    @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId, Model model){
        re.UpdateContribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId);
        return "redirect:/Contribution/View";
    }

    @GetMapping("/View")
    public String View(Model model){
//        List<Contribution> Contributions = re.ReturnContributions();
        Contribution Contry = re.ReturnContribution("01");
        List<Contribution> contris = re.ReturnContributions();
        model.addAttribute("con",Contry);
        model.addAttribute("cons",contris);
        return "Contribution/ViewContribution";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteContribution(id);
        return "redirect:/View";
    }
}