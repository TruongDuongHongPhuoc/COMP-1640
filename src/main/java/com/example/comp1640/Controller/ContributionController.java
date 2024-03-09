package com.example.comp1640.Controller;

import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/Contribution")
public class ContributionController 
{
    @Autowired
    ContributionRepository re;
    @Autowired
    ContributionService service;
    @GetMapping("/CreateContribution") // Corrected mapping without the trailing slash
    public String create() {
        return "Contribution/CreateContribution";
    }
    @PostMapping("/Hello")
    public String Create(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description,
                         @RequestParam("submitDate") String submitDate,
                         @RequestParam(value = "approve", defaultValue = "false") Boolean approve,
                         @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic, @RequestParam("accountId") String accountId,
                         @RequestParam("academicYearId") String academicYearId,
                         @RequestParam("file")MultipartFile file, Model model){
        service.CreateContribution(id,name,description,submitDate,approve,isPublic,accountId,academicYearId,file);
        System.out.println("Service Run");


//        Contribution contri = new Contribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId,path);
//        if(contri == null || contri.equals(null)){
//            System.out.println("CONTRI IS NULL");
//        }
//                re.CreateContribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId, path);
        return "redirect:/Contribution/View";
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
    @RequestParam("academicYearId") String academicYearId,
    @RequestParam("path") String path,Model model){
        re.UpdateContribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId,path);
        return "redirect:/Contribution/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Contribution> contris = re.ReturnContributions();
        model.addAttribute("cons",contris);
        return "Contribution/ViewContribution";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        re.DeleteContribution(id);
        return "redirect:/Contribution/View";
    }
}