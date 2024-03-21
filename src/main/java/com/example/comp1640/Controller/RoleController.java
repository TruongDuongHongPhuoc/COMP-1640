package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.Role;
import com.example.comp1640.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Role")
@Controller
public class RoleController 
{
    @Autowired
    RoleRepository re;

    @Autowired
    private AccountService accountService;

    @PostMapping("/Hello")
    public String CreateRole(@RequestParam("name") String name,@RequestParam("id") String id, Model model){
        accountService.checkRole("Admin");
        re.CreateRole(id, name);
        return "Role/ViewRole";
    }
    @GetMapping("/CreateRole") 
    public String CreatFul(){
        accountService.checkRole("Admin");
        return "Role/CreateRole";
    }

    @GetMapping("/Update/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
//        System.out.println(id);
accountService.checkRole("Admin");
        Role fe = re.ReturnRole(id);
        model.addAttribute("Role", fe);
        return "Role/UpdateRole";
    }
    @PostMapping("/Updating")
    public String UpdatePostRole(@RequestParam("name") String name,@RequestParam("id") String id, Model model){
        accountService.checkRole("Admin");
        re.UpdateRole(id, name);
        return "redirect:/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        accountService.checkRole("Admin");
        List<Role> Roles = re.ReturnRoles();
        model.addAttribute("Fals",Roles);
        return "Role/ViewRole";
    }

    @PostMapping("/Delete")
    public String Delete(@RequestParam("id") String id) {
        accountService.checkRole("Admin");
        re.DeleteRole(id);
        return "redirect:/View";
    }
}