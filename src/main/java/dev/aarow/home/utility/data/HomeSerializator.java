package dev.aarow.home.utility.data;

import dev.aarow.home.data.home.Home;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class HomeSerializator {

    public static String serialize(List<Home> homes){
        StringBuilder stringBuilder = new StringBuilder();

        for(Home home : homes){
            stringBuilder.append(home.getName() + "@" + LocationSerializator.serializeLocation(home.getLocation()) + ";");
        }

        return stringBuilder.isEmpty() ? "" : stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    public static List<Home> deserialize(String serialized){
        if(serialized.isEmpty()) return new ArrayList<>();

        List<Home> homes = new ArrayList<>();

        String[] homesSerialized = serialized.split(";");

        for(String homeSerialized : homesSerialized){
            String[] attributes = homeSerialized.split("@");

            String name = attributes[0];
            Location location = LocationSerializator.deserializeLocation(attributes[1]);

            homes.add(new Home(name, location));
        }

        return homes;
    }
}
