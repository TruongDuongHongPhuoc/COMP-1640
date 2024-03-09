package com.example.comp1640.repository;

import com.example.comp1640.model.Role;
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
public class RoleRepository {

    @Autowired
    MongoTemplate mongoTemplate;
    public void CreateRole(String id, String name){
        mongoTemplate.save(new Role(id, name));
    }
    public void UpdateRole(String id, String name) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name",name);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Role.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
    }
//    public List<Role> ReturnRole(){
//        List<Role> aa= mongoTemplate.findAll(Role.class);
//        return aa;
//    }
    public List<Role> ReturnRoles(){
        return mongoTemplate.findAll(Role.class,"RoleItem");
    }
    public Role ReturnRole(String id)
    {
        Role fa = mongoTemplate.findById(id, Role.class);
        return fa;
    }

    public void DeleteRole(String id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Role.class);
    }
}
