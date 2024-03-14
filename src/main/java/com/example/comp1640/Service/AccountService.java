package com.example.comp1640.Service;

import com.example.comp1640.config.SecurityConfig;
import com.example.comp1640.model.Account;
import com.example.comp1640.model.Role;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepositoryTest accountRepositoryTest;

    @Autowired
    private RoleInterface roleInterface;

    BCryptPasswordEncoder bCryptPasswordEncoder = SecurityConfig.bCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<Account> user = accountRepositoryTest.findByMail(mail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + mail + " not found");
        }
        Account account = user.get();
        return new User(
                account.getMail(),
                account.getPassword(),
                Collections.singletonList(getUserAuthen(account.getRoleId()))
        );
    }

    private GrantedAuthority getUserAuthen(String roleId) {
        Optional<Role> role = roleInterface.findById(roleId);
        return role.map(r -> new SimpleGrantedAuthority(r.getName()))
                .orElseGet(() -> new SimpleGrantedAuthority("ROLE_GUEST"));
    }
}
