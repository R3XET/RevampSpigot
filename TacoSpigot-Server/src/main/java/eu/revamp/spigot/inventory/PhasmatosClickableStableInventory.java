package eu.revamp.spigot.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PhasmatosClickableStableInventory extends PhasmatosStableInventory implements PhasmatosClickableInventory {
    private final Map<Integer, Consumer<Player>> itemActions = new HashMap<>();

    public PhasmatosClickableStableInventory(String title, int size) {
        super(title, size);
        System.out.println("[RevampSpigot] Creating inventory " + title);
    }

    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        event.setCancelled(true);
        if (this.itemActions.containsKey(event.getSlot()))
            this.itemActions.get(event.getSlot())
                    .accept((Player)event.getWhoClicked());
    }

    public PhasmatosClickableInventory addItemAction(int slot, Consumer<Player> action) {
        this.itemActions.put(slot, action);
        return this;
    }
}