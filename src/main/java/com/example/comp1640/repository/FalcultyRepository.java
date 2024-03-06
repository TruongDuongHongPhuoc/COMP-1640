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
    public void CreateFalcuty(String id, String name, int group, String Year){
        mongoTemplate.save(new Faculty( id,name,group,Year));
    }
    public void UpdateFalcuty(String id, String name, int group, String academicYear) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name",name);
        update.set("group", group);
        update.set("academicYear",academicYear);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Faculty.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
    }
    public List<Faculty> ReturnFalcuty(){
        List<Faculty> lists= mongoTemplate.findAll(Faculty.class,"FacultyItem");
        return lists;
    }
}
