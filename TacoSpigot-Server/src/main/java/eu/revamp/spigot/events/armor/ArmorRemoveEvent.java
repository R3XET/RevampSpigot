package eu.revamp.spigot.events.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorRemoveEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack oldArmor;

    public ArmorRemoveEvent(Player player, ItemStack oldArmor) {
        super(player);
        this.oldArmor = oldArmor;
    }

    public ItemStack getOldArmor() {
        return this.oldArmor;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
