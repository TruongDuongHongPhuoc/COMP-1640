package com.example.comp1640.Controller;

import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.AcademicYear;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        System.out.println("Post Run");
        service.CreateContribution(id,name,description,submitDate,approve,isPublic,accountId,academicYearId,file);
        System.out.println("Service Run");


//        Contribution contri = new Contribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId,path);
//        if(contri == null || contri.equals(null)){
//            System.out.println("CONTRI IS NULL");
//        }
//                re.CreateContribution(id, name, description, submitDate, approve, isPublic, accountId, academicYearId, path);
        return "redirect:/Contribution/View";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String updateContribution(@PathVariable String id, Model model) {
        Contribution fe = re.ReturnContribution(id);
        model.addAttribute("con", fe);
        return "Contribution/UpdateContribution";
    }
//    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
//    public String update(@PathVariable("id") String id, Model model) {
//        System.out.println(id);
//        AcademicYear fe = re.ReturnAcademicYear(id);
//        model.addAttribute("AcademicYear", fe);
//        return "AcademicYear/UpdateAcademic";
//    }
    @PostMapping("/Updating")
    public String UpdatePostContribution(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description,
    @RequestParam("submitDate") String submitDate, 
    @RequestParam(value = "approve", defaultValue = "false") Boolean approve, 
    @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic, @RequestParam("accountId") String accountId,
    @RequestParam("academicYearId") String academicYearId,
    @RequestParam("file") MultipartFile path,
    @RequestParam("oldfile")String oldfile,Model model){
        service.UpdateContribution(id,name,description,submitDate,approve,isPublic,accountId,academicYearId,path,oldfile);
        return "redirect:/Contribution/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Contribution> contris = re.ReturnContributions();
        model.addAttribute("cons",contris);
        return "Contribution/ViewContribution";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id, @RequestParam("file") String file){
        re.DeleteContribution(id);
        service.deletefile(file);
        return "redirect:/Contribution/View";
    }
}