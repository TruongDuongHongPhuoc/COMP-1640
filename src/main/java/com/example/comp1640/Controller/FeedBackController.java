package com.example.comp1640.Controller;

import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FeedbackRepository;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;

import groovyjarjarantlr4.v4.Tool.Option;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
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
    ContributionRepository conRepo;

    @Autowired
    AccountRepositoryTest accountRepoTest;

    // @PostMapping("/Create")
    // public String CreateFeedback(@RequestParam("id") String id, @RequestParam("content") String content
    //         , @RequestParam("userid") String userid, @RequestParam("contributionId") String contributionId, Model model){
    //     re.CreateFeedBack(id, content, userid, contributionId);;
    //     return "redirect:/FeedBack/View";
    // }

    // @GetMapping("/CreateFeedback")
    // public String CreatFul(){
    //     return "Feedback/CreateFeedback";
    // }

    @GetMapping("/Create/{id}") // Corrected mapping without the trailing slash
    public String update(@PathVariable String id, Model model) {
        Feedback fe = re.ReturnFeedback(id);
        Contribution con = conRepo.ReturnContribution(id);
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  
        Optional<Account> acc = accountRepoTest.findByMail(authentication.getName());
        Account accounts = acc.get();
        System.out.println(accounts);
        model.addAttribute("acc", accounts);
        model.addAttribute("Feedback", fe);
        model.addAttribute("Cons", con);
        return "Feedback/CreateFeedback";
    }
    @PostMapping("/CreateFeedBack")
    public String UpdatePostFeedback(@RequestParam("id") String id, @RequestParam("content") String content
    , @RequestParam("userid") String userid, @RequestParam("contributionId") String contributionId, Model model){
        re.CreateFeedBack(id, content, userid, contributionId);
        return "redirect:/FeedBack/View";
    }

    @GetMapping("/View")
    public String View(Model model){
        List<Feedback> feedbacks = re.ReturnFeedBacks();
        model.addAttribute("feeds",feedbacks);
        return "Feedback/ViewFeedback";
    }

    // @PostMapping("/Delete")
    // public String Delete(@RequestParam("id") String id) {
    //     re.DeleteFal(id);
    //     return "redirect:/FeedBack/View";
    // }
}
