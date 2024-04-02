package com.example.comp1640.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime lastSession;
    private String resetPasswordToken;

    @Transient
    private String roleName;
    @Transient
    private String falcutyName;
    @Transient
    private LocalDate academicYear;
    @Transient
    private LocalDate endYear;
}

