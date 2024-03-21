package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.model.Account;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.FalcultyRepository;
import com.example.comp1640.repository.PhuocAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CoordinatorController {
    @Autowired
    PhuocAccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    FalcultyRepository falcultyRepository;
    @GetMapping("/{Faculty_id}/student")
    public String ViewStudent(@PathVariable String Faculty_id, Model model){
        accountService.checkRole("Marketing Coordinator");
        List<Account> Accs = accountRepo.findAll();
        List<Account> FilledAccounts = Accs.stream()
                .filter(acc -> Objects.equals(acc.getFacultyId(), Faculty_id))
                .collect(Collectors.toList());
        model.addAttribute("accs",FilledAccounts);
        return "ViewStudent";
    }
}
