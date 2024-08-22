package dev.aarow.home.handlers;

import dev.aarow.home.HomePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BaseHandler implements Listener {

    public HomePlugin plugin = HomePlugin.getInstance();

    public BaseHandler(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
