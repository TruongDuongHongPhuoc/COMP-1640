package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document("FacultyItem")
public class Faculty {

    @Id
    private String id;
    private String name;
    private String description; // group -> description
    private String academicYear;

    public Faculty(String id, String name, String description, String academicYear) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.academicYear = academicYear;
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

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
}
