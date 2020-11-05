package eu.revamp.spigot.utils.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.Set;

public class InventoryUtils {

    public static int DEFAULT_INVENTORY_WIDTH = 9;
    public static int MINIMUM_INVENTORY_HEIGHT = 1;
    public static int MINIMUM_INVENTORY_SIZE = 9;
    public static int MAXIMUM_INVENTORY_HEIGHT = 6;
    public static int MAXIMUM_INVENTORY_SIZE = 54;
    public static int MAXIMUM_SINGLE_CHEST_SIZE = 27;
    public static int MAXIMUM_DOUBLE_CHEST_SIZE = 54;

    public static ItemStack[] deepClone(ItemStack[] origin) {
        Preconditions.checkNotNull((Object)origin, "Origin cannot be null");
        ItemStack[] cloned = new ItemStack[origin.length];
        for (int i = 0; i < origin.length; ++i) {
            ItemStack next = origin[i];
            cloned[i] = ((next == null) ? null : next.clone());
        }
        return cloned;
    }

    public static int getSafestInventorySize(int initialSize) {
        return (initialSize + 8) / 9 * 9;
    }
    @SuppressWarnings("deprecation")
    public static void removeItem(Inventory inventory, Material type, short data, int quantity) {
        ItemStack[] contents = inventory.getContents();
        boolean compareDamage = type.getMaxDurability() == 0;
        for (int i = quantity; i > 0; --i) {
            ItemStack[] array;
            int length = (array = contents).length;
            int j = 0;
            while (j < length) {
                ItemStack content = array[j];
                if (content != null && content.getType() == type && (!compareDamage || content.getData().getData() == data)) {
                    if (content.getAmount() <= 1) {
                        inventory.removeItem(content);
                        break;
                    }
                    content.setAmount(content.getAmount() - 1);
                    break;
                }
                else {
                    ++j;
                }
            }
        }
    }
    @SuppressWarnings("deprecation")
    public static int countAmount(Inventory inventory, Material type, short data) {
        ItemStack[] contents = inventory.getContents();
        boolean compareDamage = type.getMaxDurability() == 0;
        int counter = 0;
        ItemStack[] array;
        for (int length = (array = contents).length, i = 0; i < length; ++i) {
            ItemStack item = array[i];
            if (item != null && item.getType() == type && (!compareDamage || item.getData().getData() == data)) {
                counter += item.getAmount();
            }
        }
        return counter;
    }

    public static boolean isEmpty(Inventory inventory) {
        return isEmpty(inventory, true);
    }

    public static boolean isEmpty(Inventory inventory, boolean checkArmour) {
        boolean result = true;
        ItemStack[] contents2 = inventory.getContents();
        ItemStack[] array;
        for (int length = (array = contents2).length, i = 0; i < length; ++i) {
            ItemStack content = array[i];
            if (content != null && content.getType() != Material.AIR) {
                result = false;
                break;
            }
        }
        if (!result) {
            return false;
        }
        if (checkArmour && inventory instanceof PlayerInventory) {
            ItemStack[] armorContents = ((PlayerInventory)inventory).getArmorContents();
            ItemStack[] array2;
            for (int length2 = (array2 = armorContents).length, j = 0; j < length2; ++j) {
                ItemStack content2 = array2[j];
                if (content2 != null && content2.getType() != Material.AIR) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean clickedTopInventory(InventoryDragEvent event) {
        InventoryView view = event.getView();
        Inventory topInventory = view.getTopInventory();
        if (topInventory == null) {
            return false;
        }
        boolean result = false;
        Set<Map.Entry<Integer, ItemStack>> entrySet = event.getNewItems().entrySet();
        int size = topInventory.getSize();
        for (Map.Entry<Integer, ItemStack> entry : entrySet) {
            if (entry.getKey() < size) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static int getInventorySize(int listSize) {
        if (listSize <= 9) {
            return 9;
        }
        if (listSize <= 18) {
            return 18;
        }
        if (listSize <= 27) {
            return 27;
        }
        if (listSize <= 36) {
            return 36;
        }
        if (listSize <= 45) {
            return 45;
        }
        if (listSize <= 54) {
            return 54;
        }
        return 9;
    }
}
