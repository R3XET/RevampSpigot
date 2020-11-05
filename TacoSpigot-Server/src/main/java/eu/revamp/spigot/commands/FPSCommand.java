package eu.revamp.spigot.commands;

import eu.revamp.spigot.utils.chat.color.CC;
import eu.revamp.spigot.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FPSCommand extends Command {

    public FPSCommand(String name) {
        super(name);
        this.description = "Open the FPS GUI in RevampSpigot.";
        this.usageMessage = "/fps";
        this.setPermission("revampspigot.command.fps");
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            sender.sendMessage(CC.translate("&cYou do not have permission to perform this command."));
            return true;
        }
        Inventory inv = Bukkit.createInventory(null, 36, CC.DARK_GRAY + "FPS Menu");
        ItemStack panes = new ItemStack(Material.STAINED_GLASS_PANE);
        panes.setDurability((short)15);
        for (int i = 0; i < 36; ++i) {
            if (i == 11 || i == 13 || i == 15 || i == 21 || i == 23) continue;
            inv.setItem(i, panes);
        }
        ItemStack tnt =
                new ItemBuilder(Material.TNT)
                .setName(CC.GRAY + "TNT Toggle")
                .addLoreLine(" ")
                .addLoreLine(CC.GRAY + "Toggle tnt visibility off")
                .addLoreLine(" ")
                .addLoreLine(CC.translate("&7to increase &eperformance&7!"))
                .addLoreLine(CC.translate("&7Click to toggle " + (((CraftPlayer)sender).getHandle().tntDisabled ? "&aon" : "&coff") + "&7!")).toItemStack();

        inv.setItem(11, tnt);
        ItemStack sand = new ItemBuilder(Material.SAND)
                .setName(CC.GRAY + "Sand Toggle")
                .addLoreLine(" ")
                .addLoreLine(CC.GRAY + "Toggle sand visibility off")
                .addLoreLine(" ")
                .addLoreLine(CC.translate("&7to increase &eperformance&7!"))
                .addLoreLine(CC.translate("&7Click to toggle " + (((CraftPlayer)sender).getHandle().sandDisabled ? "&aon" : "&coff") + "&7!")).toItemStack();

        inv.setItem(13, sand);
        ItemStack redstone = new ItemBuilder(Material.REDSTONE_BLOCK)
                .setName(CC.GRAY + "Redstone Toggle")
                .addLoreLine(" ")
                .addLoreLine(CC.GRAY + "Toggle redstone visibility off")
                .addLoreLine(" ")
                .addLoreLine(CC.translate("&7to increase &eperformance&7!"))
                .addLoreLine(CC.translate("&7Click to toggle " + (((CraftPlayer)sender).getHandle().redstoneDisabled ? "&aon" : "&coff") + "&7!")).toItemStack();

        inv.setItem(15, redstone);
        ItemStack piston = new ItemBuilder(Material.PISTON_BASE)
                .setName(CC.GRAY + "Piston Toggle")
                .addLoreLine(" ")
                .addLoreLine(CC.GRAY + "Toggle piston visibility off")
                .addLoreLine(" ")
                .addLoreLine(CC.translate("&7to increase &eperformance&7!"))
                .addLoreLine(CC.translate("&7Click to toggle " + (((CraftPlayer)sender).getHandle().pistonsDisabled ? "&aon" : "&coff") + "&7!")).toItemStack();

        inv.setItem(21, piston);
        ItemStack all = new ItemBuilder(Material.DIAMOND)
                .setName(CC.GRAY + "Toggle All")
                .addLoreLine(" ")
                .addLoreLine(CC.GRAY + "Toggle visibility of everything")
                .addLoreLine(" ")
                .addLoreLine(CC.translate("&7off to increase &eperformance&7!"))
                .addLoreLine(CC.translate("&7Click to toggle &coff&7!")).toItemStack();
        inv.setItem(23, all);
        ((Player)sender).openInventory(inv);
        return true;
    }
}
