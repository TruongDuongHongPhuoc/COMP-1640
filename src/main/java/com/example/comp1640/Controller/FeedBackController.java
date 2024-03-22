package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FeedbackRepository;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/FeedBack")
public class FeedBackController {
    @Autowired
    FeedbackRepository re;

    @Autowired
    ContributionRepository conRepo;

    @Autowired
    AccountRepositoryTest accountRepoTest;

    @Autowired
    private AccountService accountService;

    // @PostMapping("/Create")
    // public String CreateFeedback(@RequestParam("id") String id,
    // @RequestParam("content") String content
    // , @RequestParam("userid") String userid, @RequestParam("contributionId")
    // String contributionId, Model model){
    // re.CreateFeedBack(id, content, userid, contributionId);;
    // return "redirect:/FeedBack/View";
    // }

    // @GetMapping("/CreateFeedback")
    // public String CreatFul(){
    // return "Feedback/CreateFeedback";
    // }

    @GetMapping("/Create/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
        accountService.checkRole("Marketing Coordinator");
        Feedback fe = re.ReturnFeedback(id);
        Contribution con = conRepo.ReturnContribution(id);
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Optional<Account> acc = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = acc.get();
        // System.out.println(accounts);
        model.addAttribute("acc", accounts);
        model.addAttribute("Feedback", fe);
        model.addAttribute("Cons", con);
        return "Feedback/CreateFeedback";
    }

    @PostMapping("/CreateFeedBack")
    public String UpdatePostFeedback(@RequestParam("id") String id, @RequestParam("content") String content,
            @RequestParam("userid") String userid, @RequestParam("contributionId") String contributionId, Model model) {
        accountService.checkRole("Marketing Coordinator");
        if (id.equals(null)) {
            id = UUID.randomUUID().toString();
        }
        re.CreateFeedBack(id, content, userid, contributionId);
        return "redirect:/FeedBack/View";
    }

    @PostMapping("/feedbackcreate/{idcontri}")
    public String CreateFeedback(@RequestParam("content") String content, @RequestParam("userid") String userid,
            @PathVariable("idcontri") String contributionId, Model model) {

        accountService.checkRole("Marketing Coordinator");
        String id = UUID.randomUUID().toString();

        re.CreateFeedBack(id, content, userid, contributionId);
        return "redirect:/FeedBack/View";
    }

    @GetMapping("/View")
    public String View(Model model) {
        accountService.checkRole("Marketing Coordinator");
        List<Feedback> feedbacks = re.ReturnFeedBacks();
        model.addAttribute("feeds", feedbacks);
        return "Feedback/ViewFeedback";
    }

    // @PostMapping("/Delete")
    // public String Delete(@RequestParam("id") String id) {
    // re.DeleteFal(id);
    // return "redirect:/FeedBack/View";
    // }
}