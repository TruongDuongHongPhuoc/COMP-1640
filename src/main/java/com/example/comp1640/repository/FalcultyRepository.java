package com.example.comp1640.repository;

import com.example.comp1640.model.Faculty;
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
public class FalcultyRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    public void CreateFalcuty(String id, String name, String description, String Year){
        mongoTemplate.save(new Faculty( id,name,description,Year));
    }
    public void UpdateFalcuty(String id, String name, String description, String academicYear) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name",name);
        update.set("description", description);
        update.set("academicYear",academicYear);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Faculty.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
    }
//    public List<Faculty> ReturnFalcuty(){
//        List<Faculty> aa= mongoTemplate.findAll(Faculty.class);
//        return aa;
//    }
    public List<Faculty> ReturnFaculties(){
        return mongoTemplate.findAll(Faculty.class,"FacultyItem");
    }
    public Faculty ReturnFaculty(String id)
    {
        Faculty fa = mongoTemplate.findById(id, Faculty.class);
        return fa;
    }

    public void DeleteFal(String id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Faculty.class);
    }
}
