package dev.aarow.home.commands.impl;

import dev.aarow.home.commands.BaseCommand;
import dev.aarow.home.commands.CommandInfo;
import dev.aarow.home.data.home.Home;
import dev.aarow.home.data.player.Profile;
import dev.aarow.home.utility.chat.CC;
import org.bukkit.entity.Player;

@CommandInfo(name = "deletehome", playerOnly = true)
public class DeleteHomeCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/deletehome <name>"));
            return;
        }

        Profile profile = plugin.getProfileManager().get(player);
        Home home = profile.getHomeByName(args[0]);

        if(home == null) {
            player.sendMessage(CC.translate("&7[&3&lHomes&7] &cYou don't have a home with that name."));
            return;
        }

        profile.removeHome(home);
        player.sendMessage(CC.translate("&7[&3&lHomes&7] &eYou successfully deleted the " + home.getName() + "."));
    }
}
