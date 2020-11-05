package eu.revamp.spigot.events.autorespawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerAutoRespawnEvent extends Event {
    private Player p;

    private Location deathLoc;

    private Location respawnLoc;

    private static final HandlerList handlers = new HandlerList();

    public PlayerAutoRespawnEvent(Player p, Location deathLoc, Location respawnLoc) {
        this.p = p;
        this.deathLoc = deathLoc;
        this.respawnLoc = respawnLoc;
    }

    public boolean killedByPlayer() {
        if (this.p.getLastDamageCause().getEntity() instanceof Player)
            return true;
        if (this.p.getLastDamageCause().getEntity() instanceof Projectile) {
            Projectile a = (Projectile)this.p.getLastDamageCause().getEntity();
            if (a.getShooter() instanceof Player)
                return true;
            return false;
        }
        return false;
    }

    public Player getPlayer() {
        return this.p;
    }

    public Location getDeathLocation() {
        return this.deathLoc;
    }

    public Location getRespawnLocation() {
        return this.respawnLoc;
    }

    public EntityDamageEvent.DamageCause getDeathCause() {
        return this.p.getLastDamageCause().getCause();
    }

    public Player getKiller() {
        return this.p.getKiller();
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
