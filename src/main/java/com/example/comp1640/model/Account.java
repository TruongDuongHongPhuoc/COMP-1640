package com.example.comp1640.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.expression.Dates;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Data
@Document("AccountItem")
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private String name;
    private String mail;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String password;
    private String profileImage;
    private String roleId;
    private String facultyId;
}

