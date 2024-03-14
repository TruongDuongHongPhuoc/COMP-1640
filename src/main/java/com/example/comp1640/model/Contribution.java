package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ContributionItem")
public class Contribution {
    @Id
    private String id;
    private String name;
    private String description; // typeOfFile -> description

    private String submitDate;
    private Boolean approve; // Bổ sung
    private int Status;
    private String accountId;
    private String academicYearId;
    private String path;

    public Contribution(String id, String name, String description, String submitDate, int Status, String accountId, String academicYearId, String path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.submitDate = submitDate;
        this.Status = Status;
        this.accountId = accountId;
        this.academicYearId = academicYearId;
        this.path = path;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int aStatus) {
        Status = aStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(String academicYearId) {
        this.academicYearId = academicYearId;
    }

    public String getPath_to_file() {
        return path;
    }

    public void setPath_to_file(String path) {
        this.path = path;
    }
}