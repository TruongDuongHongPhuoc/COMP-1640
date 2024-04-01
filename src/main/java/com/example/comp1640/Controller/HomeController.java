package com.example.comp1640.Controller;



import com.example.comp1640.Zip.DownloadService;
import com.example.comp1640.model.Account;
import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FalcultyRepository;
import jakarta.servlet.http.HttpServletResponse;
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
    private AccountRepositoryTest accountRepo;
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
    DownloadService downloadService;


    @GetMapping("/home")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("acc", '0');
        } else {
            Account acc = returnAccount();
            System.out.println(acc.getRoleName());
            model.addAttribute("acc", acc.getRoleId());
        }
        List<Contribution> cons = contributionService.ReturnPublicContribution();
        model.addAttribute("cons", cons);
        return "HomePage";
    }
    @GetMapping("/DownloadAllFileHome")
    public String DownloadAllFileHome(HttpServletResponse response){
        List<Contribution> cons = contributionService.ReturnPublicContribution();
        List<String> downloadList = new ArrayList<>();
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;

        for(Contribution con : cons){
            downloadList.add( directoryPath +con.getPath());
        }
        downloadService.downloadZipFile(response,downloadList);
        return "HomePage";
    }

    @GetMapping("/layout")
    public String layout() {
        return "/Layout/_Customer";
    }

    @GetMapping("/chart")
    public String getMethodName(Model model) {
        Account acc = returnAccount();
        accountService.checkRole("Marketing Manager");
        model.addAttribute("acc",acc);
        return "Dashboard/ManagerDashBoard";
    }

    @GetMapping("/chart2")
    public String getMethodName2(Model model) {
        Account acc = returnAccount();
        model.addAttribute("acc",acc);
        return "Dashboard/GuestDashBoard1";
    }

    @GetMapping("/chart1")
    public String getMethodName1(Model model) {
        accountService.checkRole("Guest");
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        accounts = accountService.getOne(accounts.getId());
        model.addAttribute("account", accounts);

        System.out.println(accounts.getFacultyId().toString().equals("02"));

        if (accounts.getFacultyId().equals("01"))
        {
            return "Dashboard/GuestDashBoard";
        }else if (accounts.getFacultyId().equals("02"))
        {
            return "Dashboard/GuestDashBoard1";
        }
        Account account = returnAccount();
        model.addAttribute("acc",account);
        return "Dashboard/GuestDashBoard";
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

    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepo.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        System.out.println(authentication.getAuthorities());
        return accounts;
    }
}
