package dev.aarow.home.commands.impl;

import dev.aarow.home.commands.BaseCommand;
import dev.aarow.home.commands.CommandInfo;
import dev.aarow.home.data.home.Home;
import dev.aarow.home.data.home.HomeTeleportRequest;
import dev.aarow.home.data.player.Profile;
import dev.aarow.home.utility.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandInfo(name = "home", playerOnly = true)
public class HomeCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/home <name>"));
            return;
        }

        Profile profile = plugin.getProfileManager().get(player);
        Home home = profile.getHomeByName(args[0]);

        if(home == null){
            player.sendMessage(CC.translate("&7[&3&lHomes&7] &cYou don't have a home with that name."));
            return;
        }

        HomeTeleportRequest homeTeleportRequest = new HomeTeleportRequest(player.getUniqueId());

        // TODO: add cancel event
        profile.setHomeTeleportRequest(homeTeleportRequest);

        player.sendMessage(CC.translate("&7[&3&lHomes&7] &eYou will teleport to the " + home.getName() + " home in 5 seconds..."));

        new BukkitRunnable(){
            public void run(){
                if(homeTeleportRequest.isCancelled()){
                    this.cancel();
                    return;
                }

                profile.setHomeTeleportRequest(null);

                if(!profile.getHomes().contains(home)){
                    this.cancel();
                    return;
                }

                player.teleport(home.getLocation());

                player.sendMessage(CC.translate("&7[&3&lHomes&7] &eSuccessfully teleported you to the " + home.getName() + " home!"));
            }
        }.runTaskLater(plugin, 100L);
    }
}
