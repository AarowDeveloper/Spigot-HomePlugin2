package dev.aarow.home.commands.impl;

import dev.aarow.home.commands.BaseCommand;
import dev.aarow.home.commands.CommandInfo;
import dev.aarow.home.data.home.Home;
import dev.aarow.home.data.player.Profile;
import dev.aarow.home.utility.chat.CC;
import dev.aarow.home.utility.data.ClickableMessage;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "homelist", playerOnly = true)
public class HomeListCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        Profile profile = plugin.getProfileManager().get(player);
        List<Home> homes = profile.getHomes();

        player.sendMessage(CC.translate("&7[&7&m-----------&7] &3&lHOME LIST &7[&7&m-----------&7]"));

        player.sendMessage(CC.translate("&bYou currently have &e" + homes.size() + " &bhome" + (homes.size() != 1 ? "s" : "") + " active&7:"));

        if(homes.isEmpty()){
            player.sendMessage(CC.translate(" &7&l• &cNone"));
        }else{
            homes.forEach(home -> new ClickableMessage()
                    .message(" &7&l• &e" + home.getName()).hover(
                    Arrays.asList("&7&m----------------------------",
                            "&3&l" + home.getName() + " Home Information",
                            "",
                            " &7&l• &fX&7: &e" + home.getLocation().getBlockX(),
                            " &7&l• &fY&7: &e" + home.getLocation().getBlockY(),
                            " &7&l• &fZ&7: &e" + home.getLocation().getBlockZ(),
                            "&7&m----------------------------"))
                    .send(player));
        }

        player.sendMessage(CC.translate("&7[&7&m-----------&7] &3&lHOME LIST &7[&7&m-----------&7]"));
    }
}
