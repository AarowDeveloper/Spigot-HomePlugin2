package dev.aarow.home.data.home;

import dev.aarow.home.HomePlugin;
import dev.aarow.home.data.player.Profile;

import java.util.UUID;

public class HomeTeleportRequest {

    private UUID uuid;
    private long requestedAt;

    public HomeTeleportRequest(UUID uuid){
        this.uuid = uuid;
        this.requestedAt = System.currentTimeMillis();
    }

    public boolean isCancelled() {
        Profile profile = HomePlugin.getInstance().getProfileManager().get(uuid);

        if(profile.getHomeTeleportRequest() == null) return true;
        if(!profile.getHomeTeleportRequest().equals(this)) return true;

        return false;
    }

    public long getRequestedAt() {
        return requestedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof HomeTeleportRequest)) return false;

        HomeTeleportRequest homeTeleportRequest = (HomeTeleportRequest) obj;

        return homeTeleportRequest.getRequestedAt() == this.requestedAt;
    }
}
