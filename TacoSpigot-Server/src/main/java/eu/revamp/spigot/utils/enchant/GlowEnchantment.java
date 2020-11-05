package eu.revamp.spigot.utils.enchant;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class GlowEnchantment extends Enchantment {
    public GlowEnchantment() {
        super(60);
    }

    public String getName() {
        return "Custom Enchantment";
    }

    public int getMaxLevel() {
        return 0;
    }

    public int getStartLevel() {
        return 0;
    }

    public EnchantmentTarget getItemTarget() {
        return null;
    }

    public boolean conflictsWith(Enchantment paramEnchantment) {
        return false;
    }

    public boolean canEnchantItem(ItemStack paramItemStack) {
        return false;
    }
}