package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.MailService;
import com.example.comp1640.config.SecurityConfig;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.FalcultyRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final FalcultyRepository falcultyRepository;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @Autowired
    SecurityConfig securityConfig;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepositoryTest repositoryTest;

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;


    @GetMapping("/register")
    public String registerPage(Model model){
        Account account = new Account();
        List<Faculty> facultyList = falcultyRepository.ReturnFaculties();
        model.addAttribute("facultyList", facultyList);
        model.addAttribute("account", account);
        return "RegisterForUser";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("account") Account account,
                                 @RequestParam("image") MultipartFile file,
                                 BindingResult result,  Model model) throws IOException {
        Optional<Account> ac = repositoryTest.findAccountByMail(account.getMail());
        if (ac.isPresent()){
            result.rejectValue("mail", "error.account", "Email is existed");
            Account user = new Account();
            List<Faculty> facultyList = falcultyRepository.ReturnFaculties();
            model.addAttribute("facultyList", facultyList);
            model.addAttribute("account", user);
            return "RegisterForUser";
        }

        account.setProfileImage(file.getOriginalFilename());
        account.setId(UUID.randomUUID().toString());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setRoleId("5");
        try {
            String fileName = file.getOriginalFilename();
            Account saveUser = repositoryTest.save(account);
            String filePath = "src/main/resources/static/images/" + fileName;
            File imageFileOnDisk = new File(filePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream((imageFileOnDisk)));
            stream.write(file.getBytes());
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return "redirect:/login";
    }

    @GetMapping("/guest/{id}")
    public String updateGuest(@PathVariable("id") String id, Model model){
        Account account = accountService.getOne(id);
        List<Account> listAccount = repositoryTest.findAll();
        for(Account a : listAccount){
            if (a.getRoleId().equals("1")) {
                mailService.SendEmail(a.getMail(),
                        "Update role",
                        "The guest with email: " + account.getMail() +" want to register as a student ");
            }
        }
        return "redirect:/home";
    }
}
