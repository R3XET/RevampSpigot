package eu.revamp.spigot.events;

import com.google.common.collect.Maps;
import eu.revamp.spigot.events.armor.ArmorEquipEvent;
import eu.revamp.spigot.events.armor.ArmorRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class ArmorEvents extends TimerTask{
    private static ConcurrentMap<UUID, ItemStack[]> contents = Maps.newConcurrentMap();
    private Player player;

    public ArmorEvents() {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            getContents().putIfAbsent(player.getUniqueId(), player.getEquipment().getArmorContents());
        });
    }

    public static void check(Player player) {
        new Timer().schedule(new ArmorEvents(player), 50L);
    }

    ArmorEvents(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        ItemStack[] now = this.player.getEquipment().getArmorContents();
        ItemStack[] saved = ArmorEvents.getContents().get(this.player.getUniqueId());
        for (int i = 0; i < now.length; ++i) {
            if (ArmorEvents.isNull(now[i]) && saved != null && !ArmorEvents.isNull(saved[i])) {
                Bukkit.getPluginManager().callEvent(new ArmorRemoveEvent(this.player, saved[i]));
            }
            else if (!ArmorEvents.isNull(now[i]) && (saved == null || ArmorEvents.isNull(saved[i]))) {
                Bukkit.getPluginManager().callEvent(new ArmorEquipEvent(this.player, now[i]));
            }
            else if (saved != null && !now[i].toString().equalsIgnoreCase(saved[i].toString())) {
                Bukkit.getPluginManager().callEvent(new ArmorRemoveEvent(this.player, saved[i]));
                Bukkit.getPluginManager().callEvent(new ArmorEquipEvent(this.player, now[i]));
            }
        }
        ArmorEvents.getContents().put(this.player.getUniqueId(), now);
    }


    public static boolean isNull(ItemStack i) {
        return i == null || i.getType() == Material.AIR;
    }

    public static ConcurrentMap<UUID, ItemStack[]> getContents() {
        return ArmorEvents.contents;
    }
}