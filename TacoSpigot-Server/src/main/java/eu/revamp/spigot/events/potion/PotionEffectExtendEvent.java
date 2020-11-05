package eu.revamp.spigot.events.potion;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

/**
 * Called when an entity's active potion effect is extended or upgraded.
 *
 * Handlers of {@link PotionEffectAddEvent} will also receive this event.
 */
public class PotionEffectExtendEvent extends PotionEffectAddEvent {

    private final PotionEffect oldEffect;

    public PotionEffectExtendEvent(LivingEntity entity, PotionEffect effect, PotionEffect oldEffect) {
        super(entity, effect);
        this.oldEffect = oldEffect;
    }

    public PotionEffect getOldEffect() {
        return this.oldEffect;
    }
}
