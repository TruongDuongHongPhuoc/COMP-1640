package com.example.comp1640.Service;

import com.example.comp1640.model.Account;
import com.example.comp1640.model.Role;
import com.example.comp1640.model.User;
import com.example.comp1640.repository.AccountRepository;
import com.example.comp1640.repository.AccountRepositoryTest;
import com.example.comp1640.repository.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService implements UserDetailsService {
    AccountRepositoryTest accountRepositoryTest;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleInterface roleInterface;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(Account account) {
        account.setId(UUID.randomUUID().toString());
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setRoleId("2");
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<Account> user = accountRepositoryTest.findByMail(mail);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong password or " + mail + " not found");
        }
        Account account = user.get();
        List<GrantedAuthority> authorities = getUserAuthen(account.getRoleId());
        return buildUserForAuthentication(account, authorities);
    }

    private List<GrantedAuthority> getUserAuthen(String roleId) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Optional<Role> rolse = roleInterface.findById(roleId);
        if (rolse.isPresent()){
            authorities.add(new SimpleGrantedAuthority(rolse.get().getName()));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }

    private UserDetails buildUserForAuthentication(Account account, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(account.getMail(), account.getPassword(), authorities);
    }
}
