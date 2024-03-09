package com.example.comp1640.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("AcademicYear")
public class AcademicYear {

    @Id
    private String id;
    private String name;
    private String yearOfAcademic; // courseNum -> yearOfAcademic (ví dụ dũ liệu: 2022 - 2023) Nghĩa là năm học bắt đầu và kết thúc năm nào
    private String startDate; // startString -> startDate
    private String endDate; // endString -> endDate

    public AcademicYear(String id, String name, String yearOfAcademic, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.yearOfAcademic = yearOfAcademic;
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

    public String getyearOfAcademic() {
        return yearOfAcademic;
    }

    public void setyearOfAcademic(String yearOfAcademic) {
        this.yearOfAcademic = yearOfAcademic;
    }

    public String getstartDate() {
        return startDate;
    }

    public void setstartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getendDate() {
        return endDate;
    }

    public void setendDate(String endDate) {
        this.endDate = endDate;
    }
}