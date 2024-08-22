package dev.aarow.home.commands.impl;

import dev.aarow.home.commands.BaseCommand;
import dev.aarow.home.commands.CommandInfo;
import dev.aarow.home.data.home.Home;
import dev.aarow.home.data.player.Profile;
import dev.aarow.home.utility.chat.CC;
import dev.aarow.home.utility.general.StringUtility;
import org.bukkit.entity.Player;

@CommandInfo(name = "sethome", playerOnly = true)
public class SetHomeCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/sethome <name>"));
            return;
        }

        if(StringUtility.containsSpecialCharacters(args[0]) || args[0].length() < 3 || args[0].length() > 16){
            player.sendMessage(CC.translate("&7[&3&lHomes&7] &cThe home name must not contain special characters and must be between 3 and 16 characters long..."));
            return;
        }


        Profile profile = plugin.getProfileManager().get(player);
        if(!player.hasPermission("homes.unlimited") && profile.getHomes().size() == 5 && profile.getHomeByName(args[0]) == null){
            player.sendMessage(CC.translate("&7[&3&lHomes&7] &cYou cannot have more than 5 homes. &7&o(use /deletehome <home>)"));
            return;
        }

        profile.addHome(args[0], player.getLocation().clone());

        player.sendMessage(CC.translate("&7[&3&lHomes&7] &eYou successfully set the home location for " + args[0] + "."));
    }
}
