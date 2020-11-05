package eu.revamp.spigot.events.potion;

import eu.revamp.spigot.events.PotionEffectEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

/**
 * Called when a potion effect is applied to an entity, or an existing effect is extended or upgraded
 */
public class PotionEffectAddEvent extends PotionEffectEvent implements Cancellable {

    private boolean cancelled;

    private static HandlerList HANDLER_LIST = new HandlerList();

    public PotionEffectAddEvent(LivingEntity entity, PotionEffect effect) {
        super(entity, effect);
    }

    @Override public HandlerList getHandlers() { return PotionEffectAddEvent.HANDLER_LIST; }

    public static HandlerList getHandlerList() { return PotionEffectAddEvent.HANDLER_LIST; }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
