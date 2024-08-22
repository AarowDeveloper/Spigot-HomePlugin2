package dev.aarow.home.data.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import dev.aarow.home.HomePlugin;
import dev.aarow.home.data.home.Home;
import dev.aarow.home.data.home.HomeTeleportRequest;
import dev.aarow.home.utility.data.HomeSerializator;
import org.bson.Document;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Profile {

    private HomePlugin plugin = HomePlugin.getInstance();

    private UUID uuid;
    private List<Home> homes;

    private HomeTeleportRequest homeTeleportRequest;

    public Profile(UUID uuid){
        this.uuid = uuid;

        this.load();
    }

    public void load(){
        Document existingDocument = plugin.getDatabaseManager().getProfiles().find(Filters.eq("UUID", uuid.toString())).first();

        if(existingDocument == null){
            this.homes = new ArrayList<>();

            this.save();
            return;
        }

        this.homes = HomeSerializator.deserialize(existingDocument.getString("HOMES"));
    }

    public void save(){
        Document existingDocument = plugin.getDatabaseManager().getProfiles().find(Filters.eq("UUID", uuid.toString())).first();
        Document document = new Document();

        document.put("UUID", uuid.toString());
        document.put("HOMES", HomeSerializator.serialize(homes));

        if(existingDocument != null){
            plugin.getDatabaseManager().getProfiles().replaceOne(Filters.eq("UUID", uuid.toString()), document, new UpdateOptions().upsert(true));
        }else{
            plugin.getDatabaseManager().getProfiles().insertOne(document);
        }
    }

    public void addHome(String name, Location location){
        if(getHomeByName(name) != null){
            Home home = this.getHomeByName(name);
            home.setLocation(location);

            this.save();
            return;
        }
        Home home = new Home(name, location);
        this.homes.add(home);

        this.save();
    }

    public void removeHome(Home home){
        this.homes.remove(home);

        this.save();
    }

    public Home getHomeByName(String name){
        return this.homes.stream().filter(home -> home.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Home> getHomes() {
        return homes;
    }

    public HomeTeleportRequest getHomeTeleportRequest() {
        return homeTeleportRequest;
    }

    public void setHomeTeleportRequest(HomeTeleportRequest homeTeleportRequest) {
        this.homeTeleportRequest = homeTeleportRequest;
    }
}
