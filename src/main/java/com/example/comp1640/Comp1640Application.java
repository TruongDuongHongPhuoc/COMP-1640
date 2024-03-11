package com.example.comp1640;


import java.util.ArrayList;
import java.util.List;

import com.example.comp1640.Store.StorageService;
import com.example.comp1640.model.Contribution;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FalcultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Comp1640Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Comp1640Application.class, args);
	}
//	THIS IS FROM PHUOC DO NOT TOUCH
//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}
	public void run(String... args) {
		
		// Clean up any previous data
		// AcademicYear.deleteAll(); // Doesn't delete the collection
		
		System.out.println("Comp1640 Application Executed");

		// System.out.println("-------------CREATE ACADEMIC YEAR ITEMS-------------------------------\n");
		
		// createGroceryItems();

	}
	// void createGroceryItems() {
	// 	List<Contribution> List = con.ReturnContributions();
	// 	for(int i=0;i<List.size();i++){
	// 		System.out.println("//////////////Loop//////////////");
	// 		System.out.println(List.get(i).getId());
	// 		System.out.println(List.get(i).getName());//name
	// 		System.out.println(List.get(i).getTypeOfFile());//typeofFile
	// 		System.out.println(List.get(i).getSubmitDate());//submiteDate
	// 		System.out.println(List.get(i).getPublic());//ispublic
	// 		System.out.println(List.get(i).getAccountId());//accountId
	// 		System.out.println(List.get(i).getAcademicYearId());//academicyearId
	// 		System.out.println("//////////////Loop//////////////");
	// 	}
	

	// CRUD operations

	//CREATE
//	void createGroceryItems() {
//		System.out.println("Data creation started...");
//
//		AcademicYear.save(new AcademicYear("001", "Year1", "01", "2024-03-01", "2024-03-02"));
//
//		System.out.println("Data creation complete...");
//	}

	// READ
	// 1. Show all the data
	//  public void showAllGroceryItems() {
		 
	// 	 itemList = AcademicYear.findAll();
		 
	// 	 itemList.forEach(item -> System.out.println(getItemDetails(item)));
	//  }
	 
	//  // 2. Get item by name
	//  public void getGroceryItemByName(String name) {
	// 	 System.out.println("Getting item by name: " + name);
	// 	 AcademicYear item = AcademicYear.findItemByName(name);
	// 	 System.out.println(getItemDetails(item));
	//  }
	 
	//  // 3. Get name and items of a all items of a particular category
	//  public void getItemsByCategory(String category) {
	// 	 System.out.println("Getting items for the category " + category);
	// 	 List<AcademicYear> list = AcademicYear.findAll(category);
		 
	// 	 list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getCourseNum()));
	//  }
	 
	//  // 4. Get count of documents in the collection
	//  public void findCountOfGroceryItems() {
	// 	 long count = AcademicYear.count();
	// 	 System.out.println("Number of documents in the collection = " + count);
	//  }
	 
	//  // UPDATE APPROACH 1: Using MongoRepository
	//  public void updateCategoryName(String category) {
		 
	// 	 // Change to this new value
	// 	 String newCategory = "munchies";
		 
	// 	 // Find all the items with the category 
	// 	 List<GroceryItem> list = groceryItemRepo.findAll(category);
		 
	// 	 list.forEach(item -> {
	// 		 // Update the category in each document
	// 		 item.setCategory(newCategory);
	// 	 });
		 
	// 	 // Save all the items in database
	// 	 List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);
		 
	// 	 if(itemsUpdated != null)
	// 		 System.out.println("Successfully updated " + itemsUpdated.size() + " items.");		 
	//  }
	 
	 
	//  // UPDATE APPROACH 2: Using MongoTemplate
	//  public void updateItemQuantity(String name, float newQuantity) {
	// 	 System.out.println("Updating quantity for " + name);
	// 	 customRepo.updateItemQuantity(name, newQuantity);
	//  }
	 
	//  // DELETE
	//  public void deleteAcademicYear(String id) {
	// 	 AcademicYear.deleteById(id);
	// 	 System.out.println("Item with id " + id + " deleted...");
	//  }
	//  // Print details in readable form
	 
	//  public String getItemDetails(AcademicYear item) {

	// 	 System.out.println(
	// 	 "Item Name: " + item.getName() + 
	// 	 ", \nItem Quantity: " + item.getItemQuantity() + 
	// 	 ", \nItem Category: " + item.getCategory()
	// 	 );
		 
	// 	 return "";
	//  }
}

