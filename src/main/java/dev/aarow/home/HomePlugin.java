package dev.aarow.home;

import dev.aarow.home.commands.impl.DeleteHomeCommand;
import dev.aarow.home.commands.impl.HomeCommand;
import dev.aarow.home.commands.impl.HomeListCommand;
import dev.aarow.home.commands.impl.SetHomeCommand;
import dev.aarow.home.handlers.impl.HomeHandler;
import dev.aarow.home.managers.impl.DatabaseManager;
import dev.aarow.home.managers.impl.ExternalAPIManager;
import dev.aarow.home.managers.impl.ProfileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HomePlugin extends JavaPlugin {

    private static HomePlugin instance;

    private ProfileManager profileManager;
    private DatabaseManager databaseManager;
    private ExternalAPIManager externalAPIManager;

    @Override
    public void onEnable() {
        instance = this;

        this.registerConfiguration();

        this.profileManager = new ProfileManager();
        this.databaseManager = new DatabaseManager();
        this.externalAPIManager = new ExternalAPIManager();

        this.registerCommands();
        this.registerHandlers();
    }

    @Override
    public void onDisable() {

    }

    protected void registerConfiguration(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    protected void registerCommands(){
        new HomeCommand();
        new SetHomeCommand();
        new HomeListCommand();
        new DeleteHomeCommand();
    }

    protected void registerHandlers(){
        new HomeHandler();
    }

    public static HomePlugin getInstance() {
        return instance;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
