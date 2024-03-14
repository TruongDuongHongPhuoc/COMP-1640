package com.example.comp1640.config;

import com.example.comp1640.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AccountService accountService;

    @Bean
    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(accountService)
                .passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/res").permitAll()
                        .requestMatchers("/forHomepage/**").permitAll()
                        .requestMatchers("/adminTemplate/**").permitAll()
                        .anyRequest().permitAll()
                ).formLogin(
                        (form) -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home")
                                .usernameParameter("email")
                                .permitAll()
                )
                .rememberMe(Customizer.withDefaults());
        return http.build();

}
}