package com.example.comp1640.Controller;


import com.example.comp1640.model.Account;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private AccountRepositoryTest repo;
    FalcultyRepository falcultyRepository;

    @Autowired
    ContributionRepository re;
    @GetMapping("/home")
    public String home(Model model){
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        if (au == null || !au.isAuthenticated()){

        }else {
            Optional<Account> optionalAccount = repo.findAccountByMail(au.getName());
            if (optionalAccount.isPresent()){
                Account account = optionalAccount.get();
                model.addAttribute("account", account);
            }
        }
        return "HomePage";
    }
        @GetMapping("/layout")
    public String layout(){
        return "/Layout/_Customer";
    }


    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "Login success!";
    }
}
