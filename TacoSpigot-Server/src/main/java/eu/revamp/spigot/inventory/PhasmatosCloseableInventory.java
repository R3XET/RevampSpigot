package eu.revamp.spigot.inventory;

import org.bukkit.event.inventory.InventoryCloseEvent;

public interface PhasmatosCloseableInventory {
  void onClose(InventoryCloseEvent paramInventoryCloseEvent);
}