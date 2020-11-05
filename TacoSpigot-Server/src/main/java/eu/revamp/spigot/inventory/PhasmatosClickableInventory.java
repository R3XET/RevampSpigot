package eu.revamp.spigot.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public interface PhasmatosClickableInventory {
  PhasmatosClickableInventory addItemAction(int paramInt, Consumer<Player> paramConsumer);

  void onClick(InventoryClickEvent paramInventoryClickEvent);
}
