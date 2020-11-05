package eu.revamp.spigot.events.potion;

import eu.revamp.spigot.events.PotionEffectEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

/**
 * Called when a potion effect is removed from an entity for whatever reason
 */
public class PotionEffectRemoveEvent extends PotionEffectEvent implements Cancellable {

    private static HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled = false;

    public PotionEffectRemoveEvent(LivingEntity entity, PotionEffect effect) {
        super(entity, effect);
    }

    @Override public HandlerList getHandlers() { return PotionEffectRemoveEvent.HANDLER_LIST; }

    public static HandlerList getHandlerList() { return PotionEffectRemoveEvent.HANDLER_LIST; }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
