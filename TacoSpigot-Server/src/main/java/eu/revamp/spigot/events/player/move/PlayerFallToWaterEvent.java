package eu.revamp.spigot.events.player.move;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerFallToWaterEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;

    private Location location;

    public PlayerFallToWaterEvent(Player player, Location location) {
        this.player = player;
        this.location = location;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getLocation() {
        return this.location;
    }
}
