package eu.revamp.spigot.listener;

import net.minecraft.server.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class FPSListener implements Listener {

    public FPSListener(JavaPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    //TODO ADDED CODE
    @EventHandler
    public void checkClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv != null && inv.getName().equals("§8FPS Menu")) {
            int slot = event.getSlot();
            event.setCancelled(true);
            if (slot == 11) {
                if (PlayerConnection.instance.player.tntDisabled) {
                    PlayerConnection.instance.player.tntDisabled = false;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled TNT visibility §a§l§oON§7§o!");
                    ItemMeta tntMeta = inv.getItem(slot).getItemMeta();
                    tntMeta.setDisplayName("§7TNT Toggle");
                    List<String> tntLore = new ArrayList<>();
                    tntLore.add(" ");
                    tntLore.add("§7Toggle tnt visibility off");
                    tntLore.add("§7to increase §eperformance§7!");
                    tntLore.add(" ");
                    tntLore.add("§7Click to toggle §coff§7!");
                    tntMeta.setLore(tntLore);
                    inv.getItem(slot).setItemMeta(tntMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                }
                else {
                    PlayerConnection.instance.player.tntDisabled = true;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled TNT visibility §c§l§oOFF§7§o!");
                    ItemMeta tntMeta = inv.getItem(slot).getItemMeta();
                    tntMeta.setDisplayName("§7TNT Toggle");
                    List<String> tntLore = new ArrayList<>();
                    tntLore.add(" ");
                    tntLore.add("§7Toggle tnt visibility off");
                    tntLore.add("§7to increase §eperformance§7!");
                    tntLore.add(" ");
                    tntLore.add("§7Click to toggle §aon§7!");
                    tntMeta.setLore(tntLore);
                    inv.getItem(slot).setItemMeta(tntMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
                }
            }
            else if (slot == 13) {
                if (PlayerConnection.instance.player.sandDisabled) {
                    PlayerConnection.instance.player.sandDisabled = false;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Sand visibility §a§l§oON§7§o!");
                    ItemMeta sandMeta = inv.getItem(slot).getItemMeta();
                    sandMeta.setDisplayName("§7Sand Toggle");
                    List<String> sandLore = new ArrayList<>();
                    sandLore.add(" ");
                    sandLore.add("§7Toggle sand visibility off");
                    sandLore.add("§7to increase §eperformance§7!");
                    sandLore.add(" ");
                    sandLore.add("§7Click to toggle §coff§7!");
                    sandMeta.setLore(sandLore);
                    inv.getItem(slot).setItemMeta(sandMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                }
                else {
                    PlayerConnection.instance.player.sandDisabled = true;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Sand visibility §c§l§oOFF§7§o!");
                    ItemMeta sandMeta = inv.getItem(slot).getItemMeta();
                    sandMeta.setDisplayName("§7Sand Toggle");
                    List<String> sandLore = new ArrayList<>();
                    sandLore.add(" ");
                    sandLore.add("§7Toggle tnt visibility off");
                    sandLore.add("§7to increase §eperformance§7!");
                    sandLore.add(" ");
                    sandLore.add("§7Click to toggle §aon§7!");
                    sandMeta.setLore(sandLore);
                    inv.getItem(slot).setItemMeta(sandMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
                }
            }
            else if (slot == 15) {
                if (PlayerConnection.instance.player.redstoneDisabled) {
                    PlayerConnection.instance.player.redstoneDisabled = false;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Redstone visibility §a§l§oON§7§o!");
                    ItemMeta redstoneMeta = inv.getItem(slot).getItemMeta();
                    redstoneMeta.setDisplayName("§7Redstone Toggle");
                    List<String> redstoneLore = new ArrayList<>();
                    redstoneLore.add(" ");
                    redstoneLore.add("§7Toggle redstone visibility off");
                    redstoneLore.add("§7to increase §eperformance§7!");
                    redstoneLore.add(" ");
                    redstoneLore.add("§7Click to toggle §coff§7!");
                    redstoneMeta.setLore(redstoneLore);
                    inv.getItem(slot).setItemMeta(redstoneMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                }
                else {
                    PlayerConnection.instance.player.redstoneDisabled = true;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Redstone visibility §c§l§oOFF§7§o!");
                    ItemMeta redstoneMeta = inv.getItem(slot).getItemMeta();
                    redstoneMeta.setDisplayName("§7Redstone Toggle");
                    List<String> redstoneLore = new ArrayList<>();
                    redstoneLore.add(" ");
                    redstoneLore.add("§7Toggle redstone visibility off");
                    redstoneLore.add("§7to increase §eperformance§7!");
                    redstoneLore.add(" ");
                    redstoneLore.add("§7Click to toggle §aon§7!");
                    redstoneMeta.setLore(redstoneLore);
                    inv.getItem(slot).setItemMeta(redstoneMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
                }
            }
            else if (slot == 21) {
                if (PlayerConnection.instance.player.pistonsDisabled) {
                    PlayerConnection.instance.player.pistonsDisabled = false;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Piston visibility §a§l§oON§7§o!");
                    ItemMeta pistonMeta = inv.getItem(slot).getItemMeta();
                    pistonMeta.setDisplayName("§7Piston Toggle");
                    List<String> pistonLore = new ArrayList<>();
                    pistonLore.add(" ");
                    pistonLore.add("§7Toggle piston visibility off");
                    pistonLore.add("§7to increase §eperformance§7!");
                    pistonLore.add(" ");
                    pistonLore.add("§7Click to toggle §coff§7!");
                    pistonMeta.setLore(pistonLore);
                    inv.getItem(slot).setItemMeta(pistonMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                }
                else {
                    PlayerConnection.instance.player.pistonsDisabled = true;
                    PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Piston visibility §c§l§oOFF§7§o!");
                    ItemMeta pistonMeta = inv.getItem(slot).getItemMeta();
                    pistonMeta.setDisplayName("§7Piston Toggle");
                    List<String> pistonLore = new ArrayList<>();
                    pistonLore.add(" ");
                    pistonLore.add("§7Toggle piston visibility off");
                    pistonLore.add("§7to increase §eperformance§7!");
                    pistonLore.add(" ");
                    pistonLore.add("§7Click to toggle §aon§7!");
                    pistonMeta.setLore(pistonLore);
                    inv.getItem(slot).setItemMeta(pistonMeta);
                    PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
                }
            }
            else if (slot == 23) {
                PlayerConnection.instance.player.tntDisabled = true;
                PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled TNT visibility §c§l§oOFF§7§o!");
                PlayerConnection.instance.player.sandDisabled = true;
                PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Sand visibility §c§l§oOFF§7§o!");
                PlayerConnection.instance.player.redstoneDisabled = true;
                PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Redstone visibility §c§l§oOFF§7§o!");
                PlayerConnection.instance.player.pistonsDisabled = true;
                PlayerConnection.instance.getPlayer().sendMessage("§7§oToggled Piston visibility §c§l§oOFF§7§o!");
                PlayerConnection.instance.getPlayer().playSound(PlayerConnection.instance.getPlayer().getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
            }
        }
    }

    //TODO ADDED CODE


}
