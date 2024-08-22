package dev.aarow.home.utility.other;

import dev.aarow.home.HomePlugin;
import org.bukkit.Bukkit;

public class Task {

    public static void runASync(Call call){
        Bukkit.getScheduler().runTaskLater(HomePlugin.getInstance(), call::call, 1L);
    }

    public interface Call {
        void call();
    }
}
