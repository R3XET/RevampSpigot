package eu.revamp.spigot.inventory;

import java.util.List;

public interface PhasmatosInventoryAPI {
  void addInventory(PhasmatosInventory paramPhasmatosInventory);

  List<PhasmatosInventory> getInventories();

  PhasmatosInventory findByTitle(String paramString);

  PhasmatosInventory findByTitleAndSize(String paramString, int paramInt);
}
