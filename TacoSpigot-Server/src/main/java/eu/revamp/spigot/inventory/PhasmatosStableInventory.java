package eu.revamp.spigot.inventory;

import eu.revamp.spigot.utils.chat.color.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PhasmatosStableInventory implements PhasmatosInventory {
    private final Inventory inventory;

    public PhasmatosStableInventory(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, CC.translate(title));
    }

    public PhasmatosInventory addItem(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
        return this;
    }

    public Map<Integer, ItemStack> getItems() {
        Map<Integer, ItemStack> items = new HashMap<>();
        for (int i = 0; i < this.inventory.getSize(); i++) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null)
                items.put(Integer.valueOf(i), item);
        }
        return items;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public int getSize() {
        return this.inventory.getSize();
    }

    public String getTitle() {
        return this.inventory.getTitle();
    }
}
