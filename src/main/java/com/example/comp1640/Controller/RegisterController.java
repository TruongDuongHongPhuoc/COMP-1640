package com.example.comp1640.Controller;

import com.example.comp1640.model.Account;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.FalcultyRepository;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.UploadContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final FalcultyRepository falcultyRepository;

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
                                 BindingResult result) {
        System.out.println(account.getMail());
        System.out.println(account.getDateOfBirth());
//        System.out.println(file.getOriginalFilename());
        return "/RegisterForUser";
    }

}
