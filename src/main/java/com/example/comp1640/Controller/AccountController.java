package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.MailService;
import com.example.comp1640.config.SecurityConfig;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.FalcultyRepository;
import com.example.comp1640.repository.FacultyRepository;
import com.example.comp1640.repository.RoleRepositoryTest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@NotNull
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepositoryTest repo;
    @Autowired
    FacultyRepository falRepo;
    @Autowired
    RoleRepositoryTest roleRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    SecurityConfig securityConfig;
    @Autowired
    MailService mailService;
    private final FalcultyRepository falcultyRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/view")
    public String view(Model model) {
        accountService.checkRole("Admin");
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        if (au == null || !au.isAuthenticated()) {
            return "Không có người dùng đang được xác thực";
        } else {
            System.out.println(au.getAuthorities());
        }
        var roles = roleRepo.findAll();
        model.addAttribute("account", accountService.getAll());
        model.addAttribute("role", roles);
        model.addAttribute("falcuty", falRepo.findAll());
        return "Account/View";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Admin");
        Account account = accountService.getOne(id);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return "Account/Detail";
    }

    @GetMapping("/reset/{id}")
    public String reset(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Admin");
        String randomPassword = accountService.generateRandomPassword();
        var roles = roleRepo.findAll();
        Account account = accountService.getOne(id);
        account.setPassword(bCryptPasswordEncoder.encode(randomPassword));
        repo.save(account);
        mailService.SendEmail(account.getMail(), "Change Password",
                "Your password has been changed to: " + randomPassword);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return "Account/Detail";
    }

    @PostMapping("/reset")
    public String reset(@RequestParam("roleId") String roleId,
            @RequestParam("id") String id,
            Model model) {
        accountService.checkRole("Admin");
        Account account = accountService.getOne(id);
        account.setRoleId(roleId);
        repo.save(account);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return "Account/Detail";
    }

    @GetMapping("/personal/{id}")
    public String personal(@PathVariable("id") String id, Model model) {
        Account account = accountService.getOne(id);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return "Account/Personal";
    }

    @PostMapping("/personal")
    public String personal(@Valid @ModelAttribute("account") Account account,
            @RequestParam("image") MultipartFile file,
            BindingResult result, Model model) throws IOException {
        Account editAccount = accountService.getOne(account.getId());
        Optional<Account> ac = repo.findAccountByMail(account.getMail());
        if (ac.isPresent()) {
            if (ac.get().getMail().equals(editAccount.getMail())) {
                editAccount.setName(account.getName());
                editAccount.setMail(account.getMail());
                editAccount.setDateOfBirth(account.getDateOfBirth());
                editAccount.setAddress(account.getAddress());
                editAccount.setPhoneNumber(account.getPhoneNumber());
                editAccount.setProfileImage(file.getOriginalFilename());
                try {
                    String fileName = file.getOriginalFilename();
                    repo.save(editAccount);
                    String filePath = "src/main/resources/static/images/" + fileName;
                    File imageFileOnDisk = new File(filePath);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream((imageFileOnDisk)));
                    stream.write(file.getBytes());
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                model.addAttribute("account", editAccount);
                model.addAttribute("faculty", falRepo.findAll());
                model.addAttribute("role", roleRepo.findAll());
                return "Account/Personal";
            } else
                return "/account/getuser";
        }
        editAccount.setName(account.getName());
        editAccount.setMail(account.getMail());
        editAccount.setDateOfBirth(account.getDateOfBirth());
        editAccount.setAddress(account.getAddress());
        editAccount.setPhoneNumber(account.getPhoneNumber());
        editAccount.setProfileImage(file.getOriginalFilename());
        try {
            String fileName = file.getOriginalFilename();
            repo.save(editAccount);
            String filePath = "src/main/resources/static/images/" + fileName;
            File imageFileOnDisk = new File(filePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream((imageFileOnDisk)));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // System.out.println(account);
        // System.out.println((editAccount));
        // System.out.println(file);
        model.addAttribute("account", editAccount);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return "Account/Personal";
    }

    @GetMapping("/getuser")
    public String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> optionalAccount = repo.findAccountByMail(authentication.getName());
        Account account = accountService.getOne(optionalAccount.get().getId());
        return "redirect:/account/personal/" + account.getId();
    }

    @GetMapping("/create")
    public String createAccount(Model model) {
        accountService.checkRole("Admin");
        Account account = new Account();
        List<Faculty> facultyList = falcultyRepository.ReturnFaculties();
        model.addAttribute("roleList", roleRepo.findAll());
        model.addAttribute("facultyList", facultyList);
        model.addAttribute("account", account);
        return "Account/CreateAccount";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("account") Account account,
            @RequestParam("image") MultipartFile file,
            BindingResult result, Model model) throws IOException {
        accountService.checkRole("Admin");
        Optional<Account> ac = repo.findAccountByMail(account.getMail());
        if (ac.isPresent()) {
            result.rejectValue("mail", "error.account", "Email is existed");
            Account a = new Account();
            List<Faculty> facultyList = falcultyRepository.ReturnFaculties();
            model.addAttribute("roleList", roleRepo.findAll());
            model.addAttribute("facultyList", facultyList);
            model.addAttribute("account", account);
            return "Account/CreateAccount";
        }

        account.setProfileImage(file.getOriginalFilename());
        account.setId(UUID.randomUUID().toString());
        // String password = accountService.generateRandomPassword();
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        // mailService.SendEmail(account.getMail(), "Create a " +
        // accountService.getOne(account.getId()).getRoleName() +" account", "Password:
        // " + password);
        try {
            String fileName = file.getOriginalFilename();
            Account saveUser = repo.save(account);
            String filePath = "src/main/resources/static/images/" + fileName;
            File imageFileOnDisk = new File(filePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream((imageFileOnDisk)));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        var roles = roleRepo.findAll();
        model.addAttribute("account", accountService.getAll());
        model.addAttribute("role", roles);
        model.addAttribute("falcuty", falRepo.findAll());
        return "Account/View";
    }
}
