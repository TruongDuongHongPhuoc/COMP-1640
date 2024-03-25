package com.example.comp1640.Controller;


import jakarta.servlet.http.HttpServletResponse;
import com.example.comp1640.Zip.DownloadService;
import com.example.comp1640.model.Account;
import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Customer;
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

import java.io.File;
import java.util.ArrayList;
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
    @Autowired
    private DownloadService downloadService;

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
    @GetMapping("/DownloadAllPublic")
    public String DownlaodPublics(HttpServletResponse response){
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;
        List<Contribution> cons = contributionService.ReturnPublicContribution();
        List<String> downloadslist = new ArrayList<>();
        for(Contribution con: cons){
            String pathfile = directoryPath + con.getPath();
            downloadslist.add(pathfile);
        }
        downloadService.downloadZipFile( response,downloadslist);
        return "redirect:/home";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Login success!";
    }

    @GetMapping("/GettingToViewWork")
    public String GettingtoViewWork() {
        accountService.checkRoleS("Student", "Marketing Coordinator");
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
