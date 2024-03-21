package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class StudentController {
    @Autowired
    ContributionRepository contributionRepository;
    @Autowired
    private ContributionService contributionService;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    AccountRepositoryTest accountRepoTest;
    @Autowired
    private AccountService accountService;

    @GetMapping("/student/{id}")
    public String ViewWork(@PathVariable String id, Model model){
        // Contribution submitDate = ; // Example submit date, replace with actual date
        // LocalDateTime currentTime = LocalDateTime.now();
        // long secondsPassed = ChronoUnit.SECONDS.between(submitDate, currentTime);
        // model.addAttribute("secondsPassed", secondsPassed);

        accountService.checkRoleS("Marketing Coordinator","Student");
        Account account = returnAccount();
        List<Contribution> cons = contributionService.ReturnAllContribution();
        List<Feedback> feds = feedbackRepository.ReturnFeedBacks();
        List<Contribution> FilteredList = cons.stream()
                .filter(con -> Objects.equals(con.getAccountId(), id))
                .collect(Collectors.toList());
        List<Feedback> FillteredFeds = feds.stream().filter(feedback -> FilteredList.stream().anyMatch(contribution -> Objects.equals(contribution.getId(), feedback.getContributionId()))).collect(Collectors.toList());

        Contribution fe = contributionRepository.ReturnContribution(id);
        model.addAttribute("con", fe);

        //hash Map
        Map<Contribution,Feedback> hash = new HashMap<>();
        for(Contribution conc : FilteredList){
            hash.put(conc,null);
            for (Feedback feedc : FillteredFeds)
            {
                if(feedc.getContributionId().equals(conc.getId())){
                    hash.put(conc,feedc);
                }
            }
        }
//        for (Map.Entry<Contribution, Feedback> entry : hash.entrySet()) {
//            Contribution key = entry.getKey();
//            Feedback value = entry.getValue();
//            if(value == null){
//                value = new Feedback("nun","nothing", account.getId(), key.getId());
//            }
//
//        }

        model.addAttribute("hashi",hash);
        model.addAttribute("cons",FilteredList);
        model.addAttribute("feds",FillteredFeds);
        model.addAttribute("accounts",account);
        return "ViewWork";
    }
    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = account.get();
        return accounts;
    }
}