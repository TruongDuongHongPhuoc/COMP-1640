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

import java.util.List;

@Component
@Service
public class AcademicYearRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    public void CreateAcademicYear(String id, String name, String courseNum, String startString, String endString){
        mongoTemplate.save(new AcademicYear(id, name, courseNum, startString, endString));
    }
    public void UpdateAcademicYear(String id, String name, String courseNum, String startString, String endString) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name",name);
        update.set("courseNum",courseNum);
        update.set("startString",startString);
        update.set("endString",endString);
        UpdateResult result = mongoTemplate.updateFirst(query, update, AcademicYear.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
    }

    public List<AcademicYear> ReturnAcademicYears(){
        return mongoTemplate.findAll(AcademicYear.class,"AcademicYearItem");
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