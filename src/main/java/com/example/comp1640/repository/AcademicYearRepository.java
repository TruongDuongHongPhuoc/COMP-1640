package com.example.comp1640.repository;

import com.example.comp1640.model.AcademicYear;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Component
@Service
public class AcademicYearRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    public void CreateAcademicYear(String id, String name, String yearOfAcademic, LocalDate startDate, LocalDate endDate){
        mongoTemplate.save(new AcademicYear(id, name, yearOfAcademic, startDate, endDate));
        System.out.println("Academic Year Repo Run");
    }
    public void UpdateAcademicYear(String id, String name, String yearOfAcademic, String startDate, String endDate) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name",name);
        update.set("yearOfAcademic",yearOfAcademic);
        update.set("startDate",startDate);
        update.set("endDate",endDate);
        UpdateResult result = mongoTemplate.updateFirst(query, update, AcademicYear.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
    }

    public List<AcademicYear> ReturnAcademicYears(){
        return mongoTemplate.findAll(AcademicYear.class,"AcademicYear");
    }
    public AcademicYear ReturnAcademicYear(String id)
    {
        AcademicYear fa = mongoTemplate.findById(id, AcademicYear.class);
        return fa;
    }
    public void DeleteAcademicYear(String id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, AcademicYear.class);
    }
}