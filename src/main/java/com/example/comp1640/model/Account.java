package com.example.comp1640.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

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

    @Transient
    private String roleName;
    @Transient
    private String falcutyName;
}

