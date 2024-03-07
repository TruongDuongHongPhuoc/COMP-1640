package com.example.comp1640.repository;

import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Faculty;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ContributionRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public void CreateContribution(String id, String name, String typeOfFile, Date submitDate, Boolean isPublic, int accountId,int academicYearId)
    {
        mongoTemplate.save(new Contribution(id, name, typeOfFile, submitDate, isPublic, accountId, academicYearId));
    }

    public void updateItemQuantity(String id, String name, String typeOfFile, Date submitDate, Boolean isPublic, int accountId, int academicYearId) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name",name);
        update.set("typeOfFile",typeOfFile);
        update.set("submitDate",submitDate);
        update.set("isPublic",isPublic);
        update.set("accountId",accountId);
        update.set("academicYearId",academicYearId);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Faculty.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");

    }


}
