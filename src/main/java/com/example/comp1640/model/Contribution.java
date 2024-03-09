package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

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

    public Contribution(String id, String name, String description, String submitDate, Boolean approve, Boolean isPublic, String accountId, String academicYearId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.submitDate = submitDate;
        this.approve = approve;
        this.isPublic = isPublic;
        this.accountId = accountId;
        this.academicYearId = academicYearId;
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

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
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
}