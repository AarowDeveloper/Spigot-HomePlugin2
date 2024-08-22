package dev.aarow.home.utility.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializator {

    // TODO: remove special characters from home names

    public static String serializeLocation(Location location){
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location deserializeLocation(String serialized){
        String[] attributes = serialized.split(",");

        World world = Bukkit.getWorld(attributes[0]);
        double x = Double.parseDouble(attributes[1]);
        double y = Double.parseDouble(attributes[2]);
        double z = Double.parseDouble(attributes[3]);
        float yaw = Float.parseFloat(attributes[4]);
        float pitch = Float.parseFloat(attributes[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
