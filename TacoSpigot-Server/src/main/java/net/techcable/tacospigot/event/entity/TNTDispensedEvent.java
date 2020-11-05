package net.techcable.tacospigot.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class TNTDispensedEvent extends EntityEvent implements Cancellable {
    private boolean cancelled;

    private static final HandlerList handlerList = new HandlerList();

    public TNTDispensedEvent(Entity tnt) {
        super(tnt);
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
