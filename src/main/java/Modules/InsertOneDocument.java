/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author avery
 */
public class InsertOneDocument {
    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")){
            MongoDatabase db = mongoClient.getDatabase("order");
            System.out.println("db name =" + db.getName());
            
            MongoCollection<Document> movieCollection = db.getCollection("movie");
            System.out.println("collection selected successfully");
            
            Document movieDocument = new Document("title", "The Bee Movie")
                    .append("description", "This is a bee movie")
                    .append("year", "2011")
                    .append("price", "399");
            
            movieCollection.insertOne(movieDocument);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
        }
    }
}
