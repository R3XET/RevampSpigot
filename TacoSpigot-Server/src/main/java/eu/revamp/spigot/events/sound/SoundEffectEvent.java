package eu.revamp.spigot.events.sound;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SoundEffectEvent extends Event implements Cancellable {

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SoundEffectEvent))
            return false;
        SoundEffectEvent other = (SoundEffectEvent)o;
        if (!other.canEqual(this))
            return false;
        Object this$entity = getEntity(), other$entity = other.getEntity();
        if ((this$entity == null) ? (other$entity != null) : !this$entity.equals(other$entity))
            return false;
        Object this$sound = getSound(), other$sound = other.getSound();
        return ((this$sound == null) ? (other$sound != null) : !this$sound.equals(other$sound)) ? false : (!(isCancelled() != other.isCancelled()));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SoundEffectEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Entity $entity = this.getEntity();
        result = result * 59 + ($entity == null ? 43 : $entity.hashCode());
        String $sound = this.getSound();
        result = result * 59 + ($sound == null ? 43 : $sound.hashCode());
        result = result * 59 + (this.isCancelled() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "SoundEffectEvent(entity=" + getEntity() + ", sound=" + getSound() + ", cancelled=" + isCancelled() + ")";
    }

    private static final HandlerList handlers = new HandlerList();

    private final Entity entity;

    private String sound;

    private boolean cancelled = false;

    public SoundEffectEvent(String sound, Entity entity) {
        this.sound = sound;
        this.entity = entity;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public String getSound() {
        return this.sound;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
