package eu.revamp.spigot.utils.player;

import eu.revamp.spigot.utils.chat.color.CC;
import eu.revamp.spigot.utils.generic.JavaUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerUtils {
    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 2F, 2F);
    }

    public static void playSound(Player player, Sound sound, float v, float v2) {
        player.playSound(player.getLocation(), sound, v, v2);
    }

    public static void playSound(Player player, String sound) {
        try {
            player.playSound(player.getLocation(), Sound.valueOf(sound), 2F, 2F);
        } catch (Exception ignored) { }
    }

    public static void playSound(Sound paramSound) {
        getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), paramSound, 2.0F, 2.0F));
    }

    public static void playSound(String paramString) {
        try {
            playSound(Sound.valueOf(paramString));
        } catch (Exception exception) {}
    }


    public static int getPing(Player who) {
        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit" /*+ bukkitversion*/ + ".entity.CraftPlayer");
            Object handle = craftPlayer.getMethod("getHandle").invoke(who);
            return (Integer) handle.getClass().getDeclaredField("ping").get(handle);
        } catch (Exception ignored) {
            return -1;
        }
    }

    public static void sendPlayer(Player player, String server, JavaPlugin plugin) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
    }

    public static Player getFinalAttacker(EntityDamageEvent ede, boolean ignoreSelf) {
        Player attacker = null;
        if (ede instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)ede;
            Entity damager = event.getDamager();
            if (event.getDamager() instanceof Player) {
                attacker = (Player)damager;
            }
            else if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile)damager;
                ProjectileSource shooter = projectile.getShooter();
                if (shooter instanceof Player) {
                    attacker = (Player)shooter;
                }
            }
            if (attacker != null && ignoreSelf && event.getEntity().equals(attacker)) {
                attacker = null;
            }
        }
        return attacker;
    }

    public static Player playerWithNameOrUUID(String string) {
        if (string == null) {
            return null;
        }
        return JavaUtils.isUUID(string) ? Bukkit.getPlayer(UUID.fromString(string)) : Bukkit.getPlayer(string);
    }

    @Deprecated
    public static OfflinePlayer offlinePlayerWithNameOrUUID(String string) {
        if (string == null) {
            return null;
        }
        return JavaUtils.isUUID(string) ? Bukkit.getOfflinePlayer(UUID.fromString(string)) : Bukkit.getOfflinePlayer(string);
    }

    public static void broadcastMessage(String string) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(CC.translate(string));
        }
    }
}
