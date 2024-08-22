package dev.aarow.home.managers.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.aarow.home.managers.Manager;
import dev.aarow.home.utility.data.MongoAuthentication;
import org.bson.Document;

import java.util.ArrayList;

public class DatabaseManager extends Manager {

    private MongoCollection<Document> profiles;

    @Override
    public void setup() {
        MongoAuthentication mongoAuthentication = new MongoAuthentication();
        MongoClient client = mongoAuthentication.get();

        MongoDatabase mongoDatabase = client.getDatabase(mongoAuthentication.getDatabase());

        this.profiles = getCollection(mongoDatabase, "profiles");
    }

    public MongoCollection<Document> getCollection(MongoDatabase mongoDatabase, String name){
        if(!mongoDatabase.listCollectionNames().into(new ArrayList<>()).contains(name)) mongoDatabase.createCollection(name);

        return mongoDatabase.getCollection(name);
    }

    public MongoCollection<Document> getProfiles() {
        return profiles;
    }
}
