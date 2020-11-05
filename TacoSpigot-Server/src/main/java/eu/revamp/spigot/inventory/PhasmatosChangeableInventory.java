package eu.revamp.spigot.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class PhasmatosChangeableInventory implements PhasmatosInventory {
    private final String title;

    private final int size;

    private final Map<Integer, ItemStack> items = new HashMap<>();

    public PhasmatosChangeableInventory(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public PhasmatosInventory addItem(int slot, ItemStack item) {
        this.items.put(slot, item);
        return this;
    }

    public Map<Integer, ItemStack> getItems() {
        return new HashMap<>(this.items);
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, this.size, this.title);
        this.items.forEach((slot, item) -> inventory.setItem(slot, item = updateItem(item, slot, player)));
        player.openInventory(inventory);
    }

    public String getTitle() {
        return this.title;
    }

    public int getSize() {
        return this.size;
    }

    public abstract ItemStack updateItem(ItemStack paramItemStack, int paramInt, Player paramPlayer);
}
