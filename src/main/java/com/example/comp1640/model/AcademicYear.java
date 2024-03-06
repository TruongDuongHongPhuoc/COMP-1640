package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("AcademicYear")
public class AcademicYear {

    @Id
    private String id;
    private String name;
    private String courseNum;
    private String startString;
    private String endString;

    public AcademicYear(String id, String name, String courseNum, String startString, String endString) {
        this.id = id;
        this.name = name;
        this.courseNum = courseNum;
        this.startString = startString;
        this.endString = endString;
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

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }
}
