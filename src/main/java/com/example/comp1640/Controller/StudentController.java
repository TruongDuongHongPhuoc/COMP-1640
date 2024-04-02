package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.Zip.DownloadService;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FeedbackRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private DownloadService downloadService;
    @GetMapping("/student/{id}")
    public String ViewWork(@PathVariable String id, Model model) {
        accountService.checkRoles("Marketing Coordinator","Student");
        Account account = returnAccount();
        List<Feedback> feds = feedbackRepository.ReturnFeedBacks();
        List<Contribution> cons = contributionService.ReturnAllContribution();
        //fill 2 list
        List<Contribution> FilteredList = cons.stream()
                .filter(con -> Objects.equals(con.getAccountId(), id))
                .collect(Collectors.toList());
        List<Feedback> FillteredFeds = feds.stream()
                .filter(feedback -> FilteredList.stream()
                        .anyMatch(contribution -> Objects.equals(contribution.getId(), feedback.getContributionId())))
                .collect(Collectors.toList());
        //send data to view
        model.addAttribute("cons",FilteredList);
        model.addAttribute("feds",FillteredFeds);
        model.addAttribute("accounts",account);
        model.addAttribute("studentid",id);
        return "ViewWork";
    }
    @GetMapping("/DownloadStudentWork/{student_id}")
    public void DownloadStudentWork(@PathVariable String student_id,HttpServletResponse response){
        List<Contribution> cons = contributionRepository.ReturnContributions();
        List<Contribution> filteredList = cons.stream()
                .filter(contribution -> contribution.getAccountId().equals(student_id))
                .collect(Collectors.toList());
        List<String> downloadList = new ArrayList<>();
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;
        for (Contribution item : filteredList){
            downloadList.add( directoryPath+item.getPath());
        }

        downloadService.downloadZipFile(response,downloadList);
    }

    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> account = accountRepoTest.findAccountByMail(authentication.getName());
        Account accounts = accountService.getOne(account.get().getId());
        return accounts;
    }

    public boolean checkDate(LocalDate currentDate, LocalDate deadline){
        if(currentDate.isBefore(deadline)){
            return true;
        }
        return false;
    }
}