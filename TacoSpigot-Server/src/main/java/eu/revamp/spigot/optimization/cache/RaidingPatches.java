package eu.revamp.spigot.optimization.cache;

import net.minecraft.server.EntityTNTPrimed;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

public class RaidingPatches {

    public RaidingPatches(){

    }

    public static Set<EntityTNTPrimed> entities = new HashSet<>();

    public static Set<Triple<Double, Double, Double>> values = new HashSet<>();

    public void fixRoofCannons() {
        Bukkit.getScheduler().runTaskTimerNoPlugin(() -> {
            try {
                HashSet<Entity> hashSet = new HashSet<>();
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity instanceof org.bukkit.entity.FallingBlock && entity.getLocation().getY() >= 256.0D)
                            hashSet.add(entity);
                    }
                }
                for (Entity entity : hashSet)
                    entity.remove();
            } catch (ConcurrentModificationException concurrentModificationException) {}
        },  10L, 10L);
    }

    public void fixSandPlate() {
        Bukkit.getScheduler().runTaskTimerNoPlugin(() -> {
            try {
                HashSet<Entity> hashSet = new HashSet<>();
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity instanceof org.bukkit.entity.FallingBlock) {
                            Location location = entity.getLocation();
                            if (location.getBlock().getType() == Material.FENCE_GATE)
                                hashSet.add(entity);
                        }
                    }
                }
                for (Entity entity : hashSet)
                    entity.remove();
            } catch (ConcurrentModificationException concurrentModificationException) {}
        },  20L, 20L);
    }
}
