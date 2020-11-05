package eu.revamp.spigot.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class PhasmatosInventoryListeners implements Listener {
    private final PhasmatosInventoryAPI inventoryAPI;

    public PhasmatosInventoryListeners(PhasmatosInventoryAPI inventoryAPI) {
        this.inventoryAPI = inventoryAPI;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player)event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (inventory == null || inventory.equals(player.getInventory()))
            return;
        PhasmatosInventory phasmatosInventory = this.inventoryAPI
                .findByTitleAndSize(inventory.getTitle(), inventory.getSize());
        if (phasmatosInventory instanceof PhasmatosClickableInventory)
            ((PhasmatosClickableInventory)phasmatosInventory).onClick(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Player player = (Player)event.getPlayer();
        Inventory inventory = event.getInventory();
        if (inventory == null || inventory.equals(player.getInventory()))
            return;
        PhasmatosInventory phasmatosInventory = this.inventoryAPI
                .findByTitleAndSize(inventory.getTitle(), inventory.getSize());
        if (phasmatosInventory instanceof PhasmatosCloseableInventory)
            ((PhasmatosCloseableInventory)phasmatosInventory).onClose(event);
    }
}
