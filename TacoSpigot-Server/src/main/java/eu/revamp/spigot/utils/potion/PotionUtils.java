package eu.revamp.spigot.utils.potion;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import net.minecraft.server.MobEffect;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PotionUtils {

    private static ImmutableSet<Object> DEBUFF_TYPES = ImmutableSet.builder().add(PotionEffectType.BLINDNESS).add(PotionEffectType.CONFUSION).add(PotionEffectType.HARM).add(PotionEffectType.HUNGER).add(PotionEffectType.POISON).add(PotionEffectType.SATURATION).add(PotionEffectType.SLOW).add(PotionEffectType.SLOW_DIGGING).add(PotionEffectType.WEAKNESS).add(PotionEffectType.WITHER).build();


    @SuppressWarnings("deprecation")
    public PotionEffect getEffect(@NonNull MobEffect effect) {
        return PotionEffectType.getById(effect.getEffectId()).createEffect(effect.getDuration(), effect.getAmplifier());
    }

    public MobEffect cloneWithDuration(final MobEffect effect, final int duration) {
        return new MobEffect(effect.getEffectId(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isShowParticles());
    }

    public void extendDuration(final MobEffect effect, final int duration) {
        effect.a(this.cloneWithDuration(effect, duration));
    }

    public static String getEffectNamesList(ArrayList<PotionEffect> effects) {
        StringBuilder names = new StringBuilder();
        for (PotionEffect effect : effects) {
            names.append(getPotionEffectName(effect.getType())).append(", ");
        }
        if (names.length() != 0) {
            names.delete(names.length() - 2, names.length());
        }
        return names.toString();
    }

    public static boolean isDebuff(PotionEffectType type) {
        return DEBUFF_TYPES.contains(type);
    }

    public static boolean isDebuff(PotionEffect potionEffect) {
        return isDebuff(potionEffect.getType());
    }

    public static boolean isDebuff(ThrownPotion thrownPotion) {
        for (PotionEffect effect : thrownPotion.getEffects()) {
            if (isDebuff(effect)) {
                return true;
            }
        }
        return false;
    }

    public static String getPotionEffectName(PotionEffectType type) {
        switch (type.getName()) {
            case "POISON": {
                return "Poison";
            }
            case "NIGHT_VISION": {
                return "Night Vision";
            }
            case "SLOW_DIGGING": {
                return "Slow Digging";
            }
            case "WITHER": {
                return "Wither";
            }
            case "INCREASE_DAMAGE": {
                return "Strength";
            }
            case "BLINDNESS": {
                return "Blindness";
            }
            case "WATER_BREATHING": {
                return "Water Breathing";
            }
            case "REGENERATION": {
                return "Regeneration";
            }
            case "ABSORPTION": {
                return "Absorption";
            }
            case "FAST_DIGGING": {
                return "Haste";
            }
            case "HEALTH_BOOST": {
                return "Health Boost";
            }
            case "HARM": {
                return "Instant Damage";
            }
            case "HEAL": {
                return "Instant Health";
            }
            case "JUMP": {
                return "Jump";
            }
            case "SLOW": {
                return "Slowness";
            }
            case "WEAKNESS": {
                return "Weakness";
            }
            case "SPEED": {
                return "Speed";
            }
            case "SATURATION": {
                return "Saturation";
            }
            case "DAMAGE_RESISTANCE": {
                return "Resistance";
            }
            case "INVISIBILITY": {
                return "Invisibility";
            }
            case "FIRE_RESISTANCE": {
                return "Fire Resistance";
            }
            case "CONFUSION": {
                return "Confusion";
            }
            case "HUNGER": {
                return "Hunger";
            }
            default:
                break;
        }
        return "";
    }
}
