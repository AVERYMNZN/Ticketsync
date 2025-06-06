/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author avery
 */
public class ConnectMongoDB {
    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoCredential mongoCredential = MongoCredential.createCredential("admin", "Content", "123".toCharArray());
            System.out.println("Connected to db successfully");
            
            MongoDatabase mongoDatabase = mongoClient.getDatabase("content");
            System.out.println(mongoCredential);
            System.out.println("db = " +  mongoDatabase.getName());
            
            mongoDatabase.createCollection("movie");
            System.out.println("Collection successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
