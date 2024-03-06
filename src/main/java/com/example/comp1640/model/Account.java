package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;

<<<<<<< HEAD
@Component
=======
>>>>>>> origin/master
@Document("AccountItem")
public class Account
{
    @Id
    private String id;
    private String name;
    private int mail;
    private Date dateOfBirth;
    private String address;
    private Byte phoneNumber;
    private String password;

    public Account(String id, String name, int mail, Date dateOfBirth, String address, Byte phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMail() {
        return mail;
    }

    public void setMail(int mail) {
        this.mail = mail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Byte phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
