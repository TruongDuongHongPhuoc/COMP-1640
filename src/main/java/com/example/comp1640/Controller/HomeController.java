package com.example.comp1640.Controller;



import com.example.comp1640.model.Account;
import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Optional;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private AccountRepositoryTest repo;
    @Autowired
    private ContributionService contributionService;
    @Autowired
    AccountRepositoryTest accountRepoTest;
    @Autowired
    FalcultyRepository falcultyRepository;

    @Autowired
    ContributionRepository re;

    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public String home(Model model){
        List<Contribution> cons = contributionService.ReturnPublicContribution();
        model.addAttribute("cons", cons);
        return "HomePage";
    }

    @GetMapping("/layout")
    public String layout() {
        return "/Layout/_Customer";
    }

    @GetMapping("/chart")
    public String getMethodName() {
        accountService.checkRole("Marketing Manager");
        return "DashBoard";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Login success!";
    }

    @GetMapping("/GettingToViewWork")
    public String GettingtoViewWork() {
        accountService.checkRoles("Student", "Marketing Coordinator");
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        return "redirect:/student/" + accounts.getId();
    }

    @GetMapping("/GettingToViewStudent")
    public String GettingToViewStudent() {
        accountService.checkRole("Marketing Coordinator");
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        return "redirect:/" + accounts.getFacultyId() + "/student";
    }
}
