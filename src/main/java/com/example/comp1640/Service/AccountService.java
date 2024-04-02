package com.example.comp1640.Service;

import com.example.comp1640.config.SecurityConfig;
import com.example.comp1640.model.*;
import com.example.comp1640.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepositoryTest accountRepositoryTest;

    @Autowired
    private RoleRepositoryTest roleRepositoryTest;

    @Autowired
    private FacultyRepository falcutyRepository;

    @Autowired
    AcademicYearRepositoryInterface academicYearRepositoryInterface;


    BCryptPasswordEncoder bCryptPasswordEncoder = SecurityConfig.bCryptPasswordEncoder();

    public List<Account> getAll(){
        return accountRepositoryTest.findAll().stream()
                .peek(account -> {
                    String roleName = roleRepositoryTest.findById(account.getRoleId()).get().getName();
                    String facultyName = "";
                    LocalDate academic = null;
                    LocalDate finalacademic = null;
                    if(account.getFacultyId() != null){
                        Faculty faculty = falcutyRepository
                                .findById(account.getFacultyId()).orElseGet(null);
                        facultyName = (faculty != null) ? faculty.getName() : "";
                        AcademicYear a = academicYearRepositoryInterface.findById(faculty.getAcademicYear()).orElse(null);
                        academic = a.getClosureDate();
                        finalacademic = a.getFinalClosureDate();
                    }
                    account.setRoleName(roleName);
                    account.setFalcutyName(facultyName);
                    account.setAcademicYear(academic);
                    account.setEndYear(finalacademic);
                }).toList();
    }

    public Account getOne(String  accountId) {
        return accountRepositoryTest.findById(accountId).map(account -> {
            String roleName = roleRepositoryTest.findById(account.getRoleId()).map(Role::getName).orElse("");
            String facultyName = "";
            LocalDate academic = null;
            LocalDate finalacademic = null;
            if (account.getFacultyId() != null) {
                Faculty faculty = falcutyRepository.findById(account.getFacultyId()).orElse(null);
                facultyName = (faculty != null) ? faculty.getName() : "";
                AcademicYear a = academicYearRepositoryInterface.findById(faculty.getAcademicYear()).orElse(null);
                academic = a.getClosureDate();
                finalacademic = a.getFinalClosureDate();
            }
            account.setRoleName(roleName);
            account.setFalcutyName(facultyName);
            account.setAcademicYear(academic);
            account.setEndYear(finalacademic);
            return account;
        }).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<Account> user = accountRepositoryTest.findAccountByMail(mail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + mail + " not found");
        }
        Account account = user.get();
        return new User(
                mail,
                account.getPassword(),
                Collections.singletonList(getUserAuthen(account.getRoleId()))
        );
    }

    private GrantedAuthority getUserAuthen(String roleId) {
        Optional<Role> role = roleRepositoryTest.findById(roleId);
        return role.map(r -> new SimpleGrantedAuthority(r.getName()))
                .orElseGet(() -> new SimpleGrantedAuthority("ROLE_GUEST"));
    }

    public String generateRandomPassword() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder();

        // Thêm ít nhất một chữ hoa
        sb.append(uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length())));

        // Thêm ít nhất một chữ thường
        sb.append(lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length())));

        // Thêm ít nhất một số
        sb.append(numbers.charAt(random.nextInt(numbers.length())));

        // Thêm các ký tự ngẫu nhiên cho đến khi đạt được độ dài tối thiểu là 8 ký tự
        while (sb.length() < 8) {
            int randomType = random.nextInt(3); // 0: uppercase, 1: lowercase, 2: numbers
            switch (randomType) {
                case 0:
                    sb.append(uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length())));
                    break;
                case 1:
                    sb.append(lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length())));
                    break;
                case 2:
                    sb.append(numbers.charAt(random.nextInt(numbers.length())));
                    break;
            }
        }

        // Trộn ngẫu nhiên chuỗi
        for (int i = 0; i < sb.length(); i++) {
            int randomIndex = random.nextInt(sb.length());
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(randomIndex));
            sb.setCharAt(randomIndex, temp);
        }
        return sb.toString();
    }

    public boolean checkRole(String role) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals(role))){
            return true;
        }else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    public boolean checkRoles(String ... roles) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                for (String role : roles) {
                    if (authority.getAuthority().equals(role)) {
                        return true;
                    }
                }
            }
        }
        throw new AccessDeniedException("Access is denied");
    }

    public void updateResetPasswordToken(String token, String email) throws AccountNotFoundException {
        Account customer = accountRepositoryTest.findAccountByMail(email).get();
        if (customer != null) {
            customer.setResetPasswordToken(token);
            accountRepositoryTest.save(customer);
        } else {
            throw new AccountNotFoundException("Could not find any customer with the email " + email);
        }
    }
    public Account getByResetPasswordToken(String token) {
        return accountRepositoryTest.findByResetPasswordToken(token);
    }

    public void updatePassword(Account customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        accountRepositoryTest.save(customer);
    }
}
