package eu.revamp.spigot.events.general;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PluginMessageSendEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    private final String channel;

    private final byte[] message;

    private boolean cancelled = false;

    public PluginMessageSendEvent(Player player, String channel, byte[] message) {
        this.player = player;
        this.channel = channel;
        this.message = message;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getChannel() {
        return this.channel;
    }

    public byte[] getMessage() {
        return this.message;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
