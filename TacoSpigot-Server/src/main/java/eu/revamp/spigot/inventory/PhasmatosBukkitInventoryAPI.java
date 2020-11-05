package eu.revamp.spigot.inventory;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class PhasmatosBukkitInventoryAPI implements PhasmatosInventoryAPI {
    private final List<PhasmatosInventory> inventories = new ArrayList<>();

    public void register(Plugin plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new PhasmatosInventoryListeners(this), plugin);
    }

    public PhasmatosInventory findByTitle(String title) {
        return this.inventories.stream()
                .filter(inventory -> inventory.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public PhasmatosInventory findByTitleAndSize(String title, int size) {
        return this.inventories.stream()
                .filter(inventory -> (inventory.getTitle().equalsIgnoreCase(title) && inventory.getSize() == size))

                .findFirst()
                .orElse(null);
    }

    public void addInventory(PhasmatosInventory inventory) {
        this.inventories.add(inventory);
    }

    public List<PhasmatosInventory> getInventories() {
        return new ArrayList<>(this.inventories);
    }
}
