package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document("ContributionItem")
public class Contribution {
    @Id
    private String id;
    private String name;
    private String typeOfFile;
    private Date submitDate;
    private Boolean isPublic;
    private int accountId;
    private int academicYearId;

    public Contribution(String id, String name, String typeOfFile, Date submitDate, Boolean isPublic, int accountId,int academicYearId) {
        this.id = id;
        this.name = name;
        this.typeOfFile = typeOfFile;
        this.submitDate = submitDate;
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

    public String getTypeOfFile() {
        return typeOfFile;
    }

    public void setTypeOfFile(String typeOfFile) {
        this.typeOfFile = typeOfFile;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(int academicYearId) {
        this.academicYearId = academicYearId;
    }
}
