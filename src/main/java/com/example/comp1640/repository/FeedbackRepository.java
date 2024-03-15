package com.example.comp1640.repository;

import com.example.comp1640.model.Feedback;
import com.example.comp1640.model.Feedback;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    public void CreateFeedBack(String id, String content, String userId, String contributionId){
        mongoTemplate.save(new Feedback(id, content, userId, contributionId));
    }
    // public void UpdateFeedBack(String id, String content, String userId, String contributionId) {
    //     Query query = new Query(Criteria.where("id").is(id));
    //     Update update = new Update();
    //     update.set("content",content);
    //     UpdateResult result = mongoTemplate.updateFirst(query, update, Feedback.class);

    //     if(result == null)
    //         System.out.println("No documents updated");
    //     else
    //         System.out.println(result.getModifiedCount() + " document(s) updated..");
    // }
  
    public List<Feedback> ReturnFeedBacks(){
        return mongoTemplate.findAll(Feedback.class,"FeedbackItem");
    }
    public Feedback ReturnFeedback(String id)
    {
        Feedback fa = mongoTemplate.findById(id, Feedback.class);
        return fa;
    }

    public void DeleteFal(String id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Feedback.class);
    }
}
