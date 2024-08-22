package dev.aarow.home.handlers.impl;

import dev.aarow.home.data.player.Profile;
import dev.aarow.home.handlers.BaseHandler;
import dev.aarow.home.utility.chat.CC;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class HomeHandler extends BaseHandler {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);

        if(profile.getHomeTeleportRequest() == null) return;
        if(event.getFrom().getX() == event.getTo().getX()
                && event.getFrom().getY() == event.getTo().getY()
                && event.getFrom().getZ() == event.getTo().getZ()) return;

        profile.setHomeTeleportRequest(null);
        player.sendMessage(CC.translate("&7[&3&lHomes&7] &cYour home teleport request has been cancelled because you moved!"));
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity().getType() != EntityType.PLAYER) return;

        Player player = (Player) event.getEntity();
        Profile profile = plugin.getProfileManager().get(player);

        if(profile.getHomeTeleportRequest() == null) return;

        profile.setHomeTeleportRequest(null);
        player.sendMessage(CC.translate("&7[&3&lHomes&7] &cYour home teleport request has been cancelled because you took damage!"));
    }
}
