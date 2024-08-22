package dev.aarow.home.managers.impl;

import dev.aarow.home.data.player.Profile;
import dev.aarow.home.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager extends Manager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void setup() {

    }

    public Profile get(String name){
        return this.get(Bukkit.getOfflinePlayer(name).getUniqueId());
    }

    public Profile get(Player player){
        return this.get(player.getUniqueId());
    }

    public Profile get(UUID uuid){
        this.profiles.putIfAbsent(uuid, new Profile(uuid));

        return this.profiles.get(uuid);
    }
}
