package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.Service.FeedbackService;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FeedbackRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.comp1640.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/FeedBack")
public class FeedBackController {
    @Autowired
    FeedbackRepository re;
    @Autowired
    FeedbackService service;

    @Autowired
    ContributionRepository conRepo;

    @Autowired
    AccountRepositoryTest accountRepoTest;

    @Autowired
    private AccountService accountService;

    @Autowired
    ContributionService contributionService;

    @GetMapping("/Create/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
        accountService.checkRole("Marketing Coordinator");
        Feedback fe = re.ReturnFeedback(id);
        Contribution con = conRepo.ReturnContribution(id);
       Account account = ReturnAccount();
        // System.out.println(accounts);
        model.addAttribute("acc", account);
        model.addAttribute("Feedback", fe);
        model.addAttribute("Cons", con);
        return "Feedback/CreateFeedback";
    }

    @PostMapping("/CreateFeedBack")
    public String UpdatePostFeedback(@RequestParam("id") String id, @RequestParam("content") String content,
            @RequestParam("userid") String userid, @RequestParam("contributionId") String contributionId, Model model) {
        accountService.checkRoles("Marketing Coordinator","Student");
        if (id.equals(null)) {
            id = UUID.randomUUID().toString();
        }
        re.CreateFeedBack(id, content, userid, contributionId);
        return "redirect:/FeedBack/View";
    }

    @PostMapping("/feedbackcreate/{idcontri}")
    public String CreateFeedback(@RequestParam("content") String content,
                                 @RequestParam("token") String token,
                                 Model model) {
        Map<String, Object> dataFromToken = JwtUtils.decodeToken(token);
        String userid = dataFromToken.get("acc").toString();
        String contributionId = dataFromToken.get("id").toString();
        accountService.checkRoles("Marketing Coordinator","Student");
        String id = UUID.randomUUID().toString();

        re.CreateFeedBack(id, content, userid, contributionId);
        return "redirect:/FeedBack/ViewFeedback/"+contributionId;
    }

    @GetMapping("/View")
    public String View(Model model) {
        Account acc = returnAccount();
        accountService.checkRole("Marketing Coordinator");
        List<Feedback> feedbacks = re.ReturnFeedBacks();
        model.addAttribute("acc",acc);
        model.addAttribute("feeds", feedbacks);
        return "Feedback/ViewFeedback";
    }
    @GetMapping("/ViewFeedback/{id}")
    public String ViewFeedback(@PathVariable String id,Model model) {
        Account account = ReturnAccount();
        List<Account> Listaccount = accountRepoTest.findAll();
        Contribution con = contributionService.attachingInfor(id);
        List<Feedback> feds = service.ReturnFeedBackWithContributionId(id);
        LocalDate Deadline = con.getSubmitDate().plusDays(14);
        LocalDate currentdate = LocalDate.now();
        int compare = Deadline.compareTo(currentdate);

        Map<String, String> dataToToken = new HashMap<>();
        dataToToken.put("id", con.getId());
        dataToToken.put("acc",account.getId());
        String dataToken = JwtUtils.generateToken(dataToToken);

        model.addAttribute("toKen",dataToken);
        model.addAttribute("isDead",compare);
        model.addAttribute("feds",feds);
        model.addAttribute("con",con);
        model.addAttribute("account",account);
        model.addAttribute("listacc",Listaccount);
        model.addAttribute("CurrentDate",currentdate);
        return "FeedBackView";
    }

    public Account ReturnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        return accounts;
    }
    // @PostMapping("/Delete")
    // public String Delete(@RequestParam("id") String id) {
    // re.DeleteFal(id);
    // return "redirect:/FeedBack/View";
    // }
    public Account returnAccount() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        return accounts;
    }
}