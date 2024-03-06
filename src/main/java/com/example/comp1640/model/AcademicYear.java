package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateAdd;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Document("AcademicYear")
public class AcademicYear {

    @Id
    private String id;
    private String name;
    private String courseNum;
    private Date startDate;
    private Date endDate;

    public AcademicYear(String id, String name, String courseNum, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.courseNum = courseNum;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
