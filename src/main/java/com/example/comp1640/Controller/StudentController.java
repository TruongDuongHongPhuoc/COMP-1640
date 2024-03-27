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
        // Contribution submitDate = ; // Example submit date, replace with actual date
        // LocalDateTime currentTime = LocalDateTime.now();
        // long secondsPassed = ChronoUnit.SECONDS.between(submitDate, currentTime);
        // model.addAttribute("secondsPassed", secondsPassed);

        accountService.checkRoles("Marketing Coordinator","Student");
        Account account = returnAccount();
        List<Contribution> cons = contributionService.ReturnAllContribution();
        List<Feedback> feds = feedbackRepository.ReturnFeedBacks();
        List<Contribution> FilteredList = cons.stream()
                .filter(con -> Objects.equals(con.getAccountId(), id))
                .collect(Collectors.toList());
        List<Feedback> FillteredFeds = feds.stream()
                .filter(feedback -> FilteredList.stream()
                        .anyMatch(contribution -> Objects.equals(contribution.getId(), feedback.getContributionId())))
                .collect(Collectors.toList());

        Contribution fe = contributionRepository.ReturnContribution(id);
        model.addAttribute("con", fe);

        // hash Map
        Map<Contribution, Feedback> hash = new HashMap<>();
        for (Contribution conc : FilteredList) {
            hash.put(conc, null);
            for (Feedback feedc : FillteredFeds) {
                if (feedc.getContributionId().equals(conc.getId())) {
                    hash.put(conc, feedc);
                }
            }
        }
        boolean closureDate = checkDate(LocalDate.now(),account.getAcademicYear());
        boolean finalClosureDate = checkDate(LocalDate.now(),account.getEndYear());
        int dateCheck = 0;
        if (closureDate && finalClosureDate){
            dateCheck = 0;
        } else if (!closureDate && finalClosureDate) {
            dateCheck = 1;
        } else if (!closureDate && !finalClosureDate) {
            dateCheck = 2;
        }
        model.addAttribute("dateCheck", dateCheck);
        model.addAttribute("finalDateCheck", finalClosureDate);
        model.addAttribute("hashi",hash);
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