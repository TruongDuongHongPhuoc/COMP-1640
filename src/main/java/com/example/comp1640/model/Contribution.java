package com.example.comp1640.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document("ContributionItem")
public class Contribution {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDate submitDate;
    private int Status;
    private String accountId;
    private String academicYearId;
    private String facultyId;
    private String path;

    @Transient
    String author;
    @Transient
    boolean canDelete;
    @Transient
    boolean canUpdate;
    @Transient
    boolean canDowload;
    @Transient
    String year;
    @Transient
    String faculty;
    public Contribution(String id, String name, String description, LocalDate submitDate, int Status, String accountId, String academicYearId, String facultyId, String path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.submitDate = submitDate;
        this.Status = Status;
        this.accountId = accountId;
        this.academicYearId = academicYearId;
        this.facultyId = facultyId;
        this.path = path;
    }

    public String getPath_to_file() {
        return path;
    }

    public void setPath_to_file(String path) {
        this.path = path;
    }
    public LocalDate CalculateDeadline(){
        LocalDate today = LocalDate.now();
        LocalDate deadline = today.plusDays(14);
        return deadline;
    }
}