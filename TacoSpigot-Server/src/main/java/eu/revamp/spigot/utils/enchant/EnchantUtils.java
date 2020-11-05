package eu.revamp.spigot.utils.enchant;

public class EnchantUtils {
    public static String getEnchantment(String name) {
        String enchant = name;

        if (name.equalsIgnoreCase("sharp") || name.equalsIgnoreCase("sharpness")) {
            enchant = "DAMAGE_ALL";
        }

        if (name.equalsIgnoreCase("ff") || name.equalsIgnoreCase("featherfalling")) {
            enchant = "FEATHER_FALLING";
        }

        if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireaspect")) {
            enchant = "FIRE_ASPECT";
        }

        if (name.equalsIgnoreCase("kb") || name.equalsIgnoreCase("knock")) {
            enchant = "KNOCKBACK";
        }

        if (name.equalsIgnoreCase("smi") || name.equalsIgnoreCase("smite")) {
            enchant = "DAMAGE_UNDEAD";
        }

        if (name.equalsIgnoreCase("bane") || name.equalsIgnoreCase("baneof") || name.equalsIgnoreCase("baneofarthropods")) {
            enchant = "DAMAGE_ARTHROPODS";
        }

        if (name.equalsIgnoreCase("prot") || name.equalsIgnoreCase("protection")) {
            enchant = "PROTECTION_ENVIRONMENTAL";
        }

        if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireprot") || name.equalsIgnoreCase("fireprotection")) {
            enchant = "PROTECTION_FIRE";
        }

        if (name.equalsIgnoreCase("blast") || name.equalsIgnoreCase("blastprot") || name.equalsIgnoreCase("blastprotection")) {
            enchant = "PROTECTION_EXPLOSIONS";
        }

        if (name.equalsIgnoreCase("proj") || name.equalsIgnoreCase("projprot") || name.equalsIgnoreCase("projectileprotection")) {
            enchant = "PROTECTION_PROJECTILE";
        }

        if (name.equalsIgnoreCase("loot") || name.equalsIgnoreCase("looting")) {
            enchant = "LOOT_BONUS_MOBS";
        }

        if (name.equalsIgnoreCase("fort") || name.equalsIgnoreCase("fortune")) {
            enchant = "LOOT_BONUS_BLOCKS";
        }

        if (name.equalsIgnoreCase("silk") || name.equalsIgnoreCase("silktouch")) {
            enchant = "SILK_TOUCH";
        }

        if (name.equalsIgnoreCase("pow") || name.equalsIgnoreCase("power")) {
            enchant = "ARROW_DAMAGE";
        }

        if (name.equalsIgnoreCase("pun") || name.equalsIgnoreCase("punch")) {
            enchant = "ARROW_KNOCKBACK";
        }

        if (name.equalsIgnoreCase("fla") || name.equalsIgnoreCase("flame")) {
            enchant = "ARROW_FIRE";
        }

        if (name.equalsIgnoreCase("inf") || name.equalsIgnoreCase("infinity")) {
            enchant = "ARROW_INFINITE";
        }

        if (name.equalsIgnoreCase("unb") || name.equalsIgnoreCase("unbreaking")) {
            enchant = "DURABILITY";
        }

        if (name.equalsIgnoreCase("eff") || name.equalsIgnoreCase("efficiency")) {
            enchant = "DIG_SPEED";
        }

        return enchant.toUpperCase();
    }
}
