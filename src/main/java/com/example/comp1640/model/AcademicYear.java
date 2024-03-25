package com.example.comp1640.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document("AcademicYear")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcademicYear {

    @Id
    private String id;
    private String name;
    private String yearOfAcademic; // courseNum -> yearOfAcademic (ví dụ dũ liệu: 2022 - 2023) Nghĩa là năm học bắt đầu và kết thúc năm nào
    private LocalDate closureDate;
    private LocalDate finalClosureDate;
}