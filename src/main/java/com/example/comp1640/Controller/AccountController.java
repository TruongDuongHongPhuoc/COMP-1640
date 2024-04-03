package com.example.comp1640.Controller;

import com.example.comp1640.Service.AccountService;
import com.example.comp1640.Service.ContributionService;
import com.example.comp1640.Service.MailService;
import com.example.comp1640.config.SecurityConfig;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Faculty;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.FalcultyRepository;
import com.example.comp1640.repository.FacultyRepository;
import com.example.comp1640.repository.RoleRepositoryTest;
import com.example.comp1640.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import com.example.comp1640.utils.JwtUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.security.auth.login.AccountNotFoundException;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    ContributionService contributionService;
    @Autowired
    JavaMailSender mailSender;
    private final FalcultyRepository falcultyRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/view")
    public String view(Model model) {
        accountService.checkRole("Admin");
        Account acc = returnAccount();
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        var roles = roleRepo.findAll();
        model.addAttribute("acc",acc);
        model.addAttribute("account", accountService.getAll());
        model.addAttribute("role", roles);
        model.addAttribute("falcuty", falRepo.findAll());
        return "Account/View";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Admin");
        Account authorite = returnAccount();
        Account account = accountService.getOne(id);
        Account acc = returnAccount();
        Map<String,String> dataToToken = new HashMap<>();
        dataToToken.put("id",account.getId());
        String token = JwtUtils.generateToken(dataToToken);

        model.addAttribute("acc",authorite);
        model.addAttribute("accountid",token);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        model.addAttribute("acc",acc);
        return "Account/Detail";
    }

    @GetMapping("/reset/{id}")
    public String reset(@PathVariable("id") String id, Model model) {
        accountService.checkRole("Admin");
        Account acc = returnAccount();
        String randomPassword = accountService.generateRandomPassword();
        var roles = roleRepo.findAll();
        Account account = accountService.getOne(id);
        account.setPassword(bCryptPasswordEncoder.encode(randomPassword));
        repo.save(account);
        mailService.SendEmail(account.getMail(), "Change Password",
                "Your password has been changed to: " + randomPassword);

        model.addAttribute("acc",acc);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return "Account/Detail";
    }

    @PostMapping("/reset")
    public String reset(@RequestParam("role") String roleId,
                        @RequestParam("token") String token,
                        Model model) {
        accountService.checkRole("Admin");
        Account acc = returnAccount();
        Map<String, Object> dataFromToken = JwtUtils.decodeToken(token);
        String id = dataFromToken.get("id").toString();
        Account account = accountService.getOne(id);
        account.setRoleId(roleId);
        repo.save(account);
        model.addAttribute("acc",acc);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return view(model);
    }

    @GetMapping("/personal/{id}")
    public String personal(@PathVariable("id") String id, Model model) {
        Account acc = returnAccount();
        Account account = accountService.getOne(id);

        List<Contribution> cons = contributionService.ReturnAllContribution();
        List<Contribution> contributions = cons.stream()
                .filter(con -> Objects.equals(con.getAccountId(), id))
                .collect(Collectors.toList());
        model.addAttribute("contributions", contributions);
        model.addAttribute("account", account);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        model.addAttribute("acc",acc);
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
                return personal(account.getId(),model);
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
        List<Contribution> cons = contributionService.ReturnAllContribution();
        List<Contribution> contributions = cons.stream()
                .filter(con -> Objects.equals(con.getAccountId(), account.getId()))
                .collect(Collectors.toList());
        model.addAttribute("contributions", contributions);
        model.addAttribute("account", editAccount);
        model.addAttribute("faculty", falRepo.findAll());
        model.addAttribute("role", roleRepo.findAll());
        return personal(account.getId(),model);
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
        Account acc = returnAccount();
        List<Faculty> facultyList = falcultyRepository.ReturnFaculties();
        model.addAttribute("roleList", roleRepo.findAll());
        model.addAttribute("facultyList", facultyList);
        model.addAttribute("account", account);
        model.addAttribute("acc",acc);
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
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setLastSession(null);
        repo.save(account);
        mailService.SendEmail(account.getMail(), "Create a new account", "Password: " + account.getPassword());
        try {
            String fileName = file.getOriginalFilename();
            String filePath = "src/main/resources/static/images/" + fileName;
            File imageFileOnDisk = new File(filePath);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream((imageFileOnDisk)));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Account acc = returnAccount();
        model.addAttribute("acc", acc);
        return view(model);
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(HttpServletRequest request, Model model){
        UUID uuid = UUID.randomUUID();
        String email = request.getParameter("email");
        String token = uuid.toString().replaceAll("-", "").substring(0, 8);
        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/account/reset_password?token="+token;
            sendEmail(email, resetPasswordLink);
        } catch (AccountNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return "Login";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Account customer = accountService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Account customer = accountService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            accountService.updatePassword(customer, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "Login";
    }

    public Account returnAccount(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> acc = repo.findAccountByMail(authentication.getName());
        Account account = acc.get();
        account = accountService.getOne(account.getId());
        return account;
    }

    @GetMapping("/guest/{id}")
    public String updateGuest(@PathVariable("id") String id, Model model){
        Account account = accountService.getOne(id);
        List<Account> listAccount = repo.findAll();
        for(Account a : listAccount){
            if (a.getRoleId().equals("1")) {
                mailService.SendEmail(a.getMail(),
                        "Update role",
                        "The guest with email: " + account.getMail() +" want to register as a student ");
            }
        }
        return personal(account.getId(), model);
    }
}
