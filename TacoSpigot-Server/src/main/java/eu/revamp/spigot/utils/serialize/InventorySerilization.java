package eu.revamp.spigot.utils.serialize;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InventorySerilization {
    public static ItemStack[] fixInventoryOrder(ItemStack[] paramArrayOfItemStack) {
        ItemStack[] arrayOfItemStack = new ItemStack[36];
        System.arraycopy(paramArrayOfItemStack, 0, arrayOfItemStack, 27, 9);
        System.arraycopy(paramArrayOfItemStack, 9, arrayOfItemStack, 0, 27);
        return arrayOfItemStack;
    }

    public static String serializeInventory(ItemStack[] paramArrayOfItemStack) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ItemStack itemStack : paramArrayOfItemStack) {
            stringBuilder.append(serializeItemStack(itemStack));
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

    public static ItemStack[] deserializeInventory(String paramString) {
        if (!paramString.contains(";"))
            return null;
        ArrayList<ItemStack> arrayList = new ArrayList<>();
        String[] arrayOfString = paramString.split(";");
        for (String str : arrayOfString)
            arrayList.add(deserializeItemStack(str));
        return arrayList.toArray(new ItemStack[0]);
    }

    @SuppressWarnings("deprecation")
    public static String serializeItemStack(ItemStack paramItemStack) {
        StringBuilder stringBuilder = new StringBuilder();
        if (paramItemStack == null)
            return "null";
        String str = String.valueOf(paramItemStack.getType().getId());
        stringBuilder.append("t@").append(str);
        if (paramItemStack.getDurability() != 0) {
            String str1 = String.valueOf(paramItemStack.getDurability());
            stringBuilder.append(":d@").append(str1);
        }
        if (paramItemStack.getAmount() != 1) {
            String str1 = String.valueOf(paramItemStack.getAmount());
            stringBuilder.append(":a@").append(str1);
        }
        Map<Enchantment, Integer> map = paramItemStack.getEnchantments();
        if (map.size() > 0) {
            for (Map.Entry<Enchantment, Integer> ench : map.entrySet()) {
                stringBuilder.append(":e@").append(ench.getKey().getId()).append("@").append(ench.getValue());
            }
        }
        if (paramItemStack.hasItemMeta()) {
            ItemMeta itemMeta = paramItemStack.getItemMeta();
            if (itemMeta.hasDisplayName())
                stringBuilder.append(":dn@").append(itemMeta.getDisplayName());
            if (itemMeta.hasLore())
                stringBuilder.append(":l@").append(itemMeta.getLore());
        }
        return stringBuilder.toString();
    }

    @SuppressWarnings("deprecation")
    public static ItemStack deserializeItemStack(String paramString) {
        ItemStack itemStack = null;
        ItemMeta itemMeta = null;
        if (paramString.equals("null"))
            return new ItemStack(Material.AIR);
        String[] arrayOfString = paramString.split(":");
        for (String str1 : arrayOfString) {
            List<String> list;
            byte b;
            String[] arrayOfString1 = str1.split("@");
            String str2 = arrayOfString1[0];
            switch (str2) {
                case "t":
                    itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(arrayOfString1[1])));
                    itemMeta = itemStack.getItemMeta();
                    break;
                case "d":
                    if (itemStack != null)
                        itemStack.setDurability(Short.parseShort(arrayOfString1[1]));
                    break;
                case "a":
                    if (itemStack != null)
                        itemStack.setAmount(Integer.parseInt(arrayOfString1[1]));
                    break;
                case "e":
                    if (itemStack != null)
                        itemStack.addEnchantment(Enchantment.getById(Integer.parseInt(arrayOfString1[1])), Integer.parseInt(arrayOfString1[2]));
                    break;
                case "dn":
                    if (itemMeta != null)
                        itemMeta.setDisplayName(arrayOfString1[1]);
                    break;
                case "l":
                    arrayOfString1[1] = arrayOfString1[1].replace("[", "");
                    arrayOfString1[1] = arrayOfString1[1].replace("]", "");
                    list = Arrays.asList(arrayOfString1[1].split(","));
                    for (b = 0; b < list.size(); b++) {
                        String str = list.get(b);
                        if (str != null && (str.toCharArray()).length != 0) {
                            if (str.charAt(0) == ' ')
                                str = str.replaceFirst(" ", "");
                            list.set(b, str);
                        }
                    }
                    if (itemMeta != null)
                        itemMeta.setLore(list);
                    break;
            }
        }
        if (itemMeta != null && (itemMeta.hasDisplayName() || itemMeta.hasLore()))
            itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

