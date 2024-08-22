package dev.aarow.home.managers.impl;

import dev.aarow.home.HomePlugin;
import dev.aarow.home.data.externalapi.RetrieveDataAction;
import dev.aarow.home.data.player.Profile;
import dev.aarow.home.managers.Manager;
import dev.aarow.home.utility.data.HomeSerializator;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;

public class ExternalAPIManager extends Manager {

    private final String API_KEY;

    public ExternalAPIManager(){
        API_KEY = HomePlugin.getInstance().getConfig().getString("SPECIAL-API-ACCESS-KEY");
    }

    @Override
    public void setup() {
        try(ServerSocket serverSocket = new ServerSocket(1322)){
            Socket socket = serverSocket.accept();

            new ClientHandler(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ClientHandler {

        public ClientHandler(Socket socket){
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String jsonAsString = in.readLine();
                if(jsonAsString == null) return;

                JSONObject jsonObject = new JSONObject(jsonAsString);
                if(jsonObject.getString("api_key") == null
                        || jsonObject.getString("retrieve_data_action") == null
                || jsonObject.getString("uuid") == null) return;

                if(!jsonObject.getString("api_key").equals(API_KEY)) return;

                RetrieveDataAction retrieveDataAction = RetrieveDataAction.valueOf(jsonObject.getString("retrieve_data_action"));
                UUID uuid = UUID.fromString(jsonObject.getString("uuid"));

                switch(retrieveDataAction){
                    case HOMES:
                        handleHomesDataAction(out, uuid);
                        return;
                    case HOME_DATA:
                        if(jsonObject.getString("home") == null) return;

                        handleHomeDataAction(jsonObject.getString("name"), out, uuid);
                        return;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void handleHomesDataAction(PrintWriter out, UUID uuid){
            if(!plugin.getProfileManager().exists(uuid)) return;

            Profile profile = plugin.getProfileManager().get(uuid);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid.toString());
            jsonObject.put("homes", HomeSerializator.serialize(profile.getHomes()));

            out.println(jsonObject);
        }

        private void handleHomeDataAction(String name, PrintWriter out, UUID uuid){
            if(!plugin.getProfileManager().exists(uuid)) return;

            Profile profile = plugin.getProfileManager().get(uuid);
            if(profile.getHomeByName(name) == null) return;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid.toString());
            jsonObject.put("home", HomeSerializator.serialize(Arrays.asList(profile.getHomeByName(name))));

            out.println(jsonObject);
        }
    }
}
