package com.example.comp1640.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document("FacultyItem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  Faculty {
    @Id
    private String id;
    private String name;
    private String description; // group -> description
    private String academicYear;

}
