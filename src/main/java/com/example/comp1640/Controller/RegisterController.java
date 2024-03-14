package com.example.comp1640.Controller;

import com.example.comp1640.config.SecurityConfig;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.FalcultyRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Paths;
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
            result.rejectValue("mail", "error.acount", "Email is existed");
            Account user = new Account();
            List<Faculty> facultyList = falcultyRepository.ReturnFaculties();
            model.addAttribute("facultyList", facultyList);
            model.addAttribute("account", user);
            return "RegisterForUser";
        }

        account.setProfileImage(file.getOriginalFilename());
        account.setId(UUID.randomUUID().toString());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setRoleId("2");
        try {
            String fileName = file.getOriginalFilename();
            Account saveUser = repositoryTest.save(account);
            String filePath = "src/main/resources/images/" + fileName;
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
}
