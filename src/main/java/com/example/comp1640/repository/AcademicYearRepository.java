package com.example.comp1640.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.comp1640.model.AcademicYear;
import com.mongodb.client.result.UpdateResult;

import java.util.Date;
@Component
public class AcademicYearRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void updateItemQuantity(String id, String name, String courseNum, Date startDate, Date endDate) {
		Query query = new Query(Criteria.where("name").is(name));
		Update update = new Update();
		update.set("id", id);
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, AcademicYear.class);
		
		if(result == null)
			System.out.println("No documents updated");
		else
			System.out.println(result.getModifiedCount() + " document(s) updated..");

	}

}
