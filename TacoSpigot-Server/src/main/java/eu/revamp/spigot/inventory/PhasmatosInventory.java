package eu.revamp.spigot.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface PhasmatosInventory {
  void open(Player paramPlayer);

  PhasmatosInventory addItem(int paramInt, ItemStack paramItemStack);

  Map<Integer, ItemStack> getItems();

  String getTitle();

  int getSize();
}
