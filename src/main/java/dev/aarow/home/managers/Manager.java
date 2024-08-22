package dev.aarow.home.managers;

import dev.aarow.home.HomePlugin;
import dev.aarow.home.utility.other.Task;

public abstract class Manager {

    public HomePlugin plugin = HomePlugin.getInstance();

    public Manager(){
        Task.runASync(this::setup);
    }

    public abstract void setup();
}
