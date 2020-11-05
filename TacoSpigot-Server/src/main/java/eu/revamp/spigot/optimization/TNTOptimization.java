package eu.revamp.spigot.optimization;

import com.google.common.base.Predicate;
import eu.revamp.spigot.config.Settings;
import eu.revamp.spigot.optimization.cache.*;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityTNTPrimed;
import net.minecraft.server.Explosion;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftTNTPrimed;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import java.util.*;

public class TNTOptimization implements Runnable, Predicate{
    public static ArrayList<org.bukkit.util.Vector> a2s = new ArrayList<>();
    public static Explosion cancelledTNT = new Explosion(null, null, 0.0d, 0.0d, 0.0d, 0.0f, false, false, 0);
    public static boolean compressing = Settings.IMP.SETTINGS.PERFORMANCE.TNT.COMBINE_TNT;
    public static ArrayList<Double> d7s = new ArrayList<>();
    public static ArrayList<EntityTNTPrimed> entities = new ArrayList<>();
    public static HashMap<Coordinate, List<Entity>> entityListCache = new HashMap<>();
    public static ArrayList<Boolean> example = new ArrayList<>();
    public static HashMap<BlockPosition, Integer> obbyDuras = new HashMap<>();
    public static Predicate<Entity> predicate = new TNTOptimization();

    public static HashMap<SandLocation, SandCache> sandMoveCache = new HashMap<>();
    public static HashSet<CoordinateInt> spongeValues = new HashSet<>();
    public static HashMap<TNTLocation, TNTCache> tntMoveCache = new HashMap<>();
    public static Set<Triple<Double, Double, Double>> values = new HashSet<>();
    public static ArrayList<Vector> velocities = new ArrayList<>();

    public void checkEntities() {
        org.bukkit.Bukkit.getScheduler().runTaskTimerNoPlugin(new TNTOptimization(), 1L, 1L);
    }
        public static double distance (EntityTNTPrimed entity){
            Location original = entity.sourceLoc;
            Location neww = entity.getBukkitEntity().getLocation();
            try {
                return Math.sqrt(Math.pow(neww.getX() - original.getX(), 2.0d) + Math.pow(neww.getZ() - original.getZ(), 2.0d));
            } catch (Exception e) {
                return 0.0d;
            }
        }


    public boolean apply(net.minecraft.server.Entity a) {
        return !a.dead && a.isTNTorSand;
    }

    public boolean apply(Object a) {
        return this.apply((net.minecraft.server.Entity)a);
    }



    @Override
    public void run() {
        if (!TNTOptimization.compressing) {
            return;
        }
        Set<EntityTNTPrimed> toRemove = new HashSet<>();
        for (EntityTNTPrimed e : TNTOptimization.entities) {
            if (e.isAlive()) {
                Location location = e.getBukkitEntity().getLocation();
                int amount = 0;
                Set<CraftEntity> toRemoveAfterMultiplier = new HashSet<>();
                for (org.bukkit.entity.Entity eLooped : location.getWorld().getNearbyEntities(location, -0.1, -0.1, -0.1)) {
                    if (eLooped != null && eLooped instanceof TNTPrimed && eLooped != e.getBukkitEntity()) {
                        TNTPrimed tnt = (TNTPrimed)eLooped;
                        if (!tnt.getLocation().equals(e.getBukkitEntity().getLocation()) || tnt.getTicksLived() != e.ticksLived) {
                            continue;
                        }
                        toRemoveAfterMultiplier.add((org.bukkit.craftbukkit.entity.CraftEntity)eLooped);
                        EntityTNTPrimed eTNT = ((CraftTNTPrimed)eLooped).getHandle();
                        toRemove.add(eTNT);
                        amount += eTNT.multiplier;
                    }
                }
                for (CraftEntity remove : toRemoveAfterMultiplier) {
                    remove.remove();
                    remove.setCanExplode(false);
                }
                EntityTNTPrimed entityTNTPrimed = e;
                entityTNTPrimed.multiplier += amount;
            }
            else {
                toRemove.add(e);
            }
        }
        TNTOptimization.entities.removeAll(toRemove);
    }



}
