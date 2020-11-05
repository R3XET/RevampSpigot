package eu.revamp.spigot.events.general;

import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;

public class ServerReloadEvent extends ServerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Server server;

    public ServerReloadEvent(Server server) {
        this.server = server;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Server getServer() {
        return this.server;
    }
}
