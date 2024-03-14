package com.example.comp1640.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Data
@Builder
@Document("AccountItem")
public class Account {
    @Id
    private String id;
    private String name;
    private String mail;
    private Date dateOfBirth;
    private String address;
    private Byte phoneNumber;
    private String password;
    private String profileImage;
    private String roleId;
    private String facultyId;

    public Account(String id, String name, String mail, Date dateOfBirth, String address, Byte phoneNumber, String password, String profileImage, String roleId, String facultyId) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profileImage = profileImage;
        this.roleId = roleId;
        this.facultyId = facultyId;
    }

}

