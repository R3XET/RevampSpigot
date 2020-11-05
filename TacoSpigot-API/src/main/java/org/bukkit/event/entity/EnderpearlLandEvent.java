package org.bukkit.event.entity;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EnderpearlLandEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public Reason reason;

    public boolean cancel;

    public Entity hit;

    public EnderpearlLandEvent(EnderPearl enderPearl, Reason reason, Entity hit) {
        super(enderPearl);
        this.reason = reason;
        this.hit = hit;
    }

    public EnderPearl getEntity() {
        return (EnderPearl)super.getEntity();
    }

    public Reason getReason() {
        return this.reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Entity getHit() {
        return this.hit;
    }

    public enum Reason {
        BLOCK, ENTITY
    }
}
