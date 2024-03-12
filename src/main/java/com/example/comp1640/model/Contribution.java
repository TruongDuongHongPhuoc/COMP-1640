package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Document("ContributionItem")
public class Contribution {
    @Id
    private String id;
    private String name;
    private String description; // typeOfFile -> description
    private String submitDate;
    private Boolean approve; // Bổ sung
    private Boolean isPublic;
    private String accountId;
    private String academicYearId;
    private String facultyId;
    private String path;

    public Contribution(String id, String name, String description, String submitDate, Boolean approve, Boolean isPublic, String accountId, String academicYearId, String facultyId, String path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.submitDate = submitDate;
        this.approve = approve;
        this.isPublic = isPublic;
        this.accountId = accountId;
        this.academicYearId = academicYearId;
        this.facultyId = facultyId;
        this.path = path;
    }

}