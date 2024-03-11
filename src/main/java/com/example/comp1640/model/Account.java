package com.example.comp1640.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
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
    private String roleId;

    public Account(String id, String name, String mail, Date dateOfBirth, String address, Byte phoneNumber, String password, String roleId) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roleId = roleId;
    }
}

