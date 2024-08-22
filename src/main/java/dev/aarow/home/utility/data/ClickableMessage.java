package dev.aarow.home.utility.data;

import dev.aarow.home.utility.chat.CC;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ClickableMessage {

    private String message;
    private String hoverLines;

    public ClickableMessage message(String name){
        this.message = name;
        return this;
    }

    public ClickableMessage hover(List<String> lines){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < lines.size(); i++){
            if(i == lines.size()-1){
                stringBuilder.append(lines.get(i));
                break;
            }

            stringBuilder.append(lines.get(i) + "\n");
        }

        this.hoverLines = stringBuilder.toString();
        return this;
    }

    public void send(Player player){
        TextComponent textComponent = new TextComponent(CC.translate(message));

        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                new TextComponent(CC.translate(hoverLines))
        }));

        player.spigot().sendMessage(textComponent);
    }
}
