package eu.revamp.spigot.events.player.attack;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerAttackEvent extends PlayerEvent implements Cancellable {

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PlayerAttackEvent))
            return false;
        PlayerAttackEvent other = (PlayerAttackEvent)o;
        if (!other.canEqual(this))
            return false;
        if (isCancelled() != other.isCancelled())
            return false;
        Object this$attacker = getAttacker(), other$attacker = other.getAttacker();
        if ((this$attacker == null) ? (other$attacker != null) : !this$attacker.equals(other$attacker))
            return false;
        Object this$victim = getVictim(), other$victim = other.getVictim();
        return !((this$victim == null) ? (other$victim != null) : !this$victim.equals(other$victim));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PlayerAttackEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isCancelled() ? 79 : 97);
        Player $attacker = this.getAttacker();
        result = result * 59 + ($attacker == null ? 43 : $attacker.hashCode());
        Player $victim = this.getVictim();
        result = result * 59 + ($victim == null ? 43 : $victim.hashCode());
        return result;
    }

    public String toString() {
        return "PlayerAttackEvent(cancelled=" + isCancelled() + ", attacker=" + getAttacker() + ", victim=" + getVictim() + ")";
    }

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private Player attacker;

    private Player victim;


    public PlayerAttackEvent(Player attacker, Player victim) {
        super(attacker);
        this.victim = victim;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setAttacker(Player attacker) {
        this.attacker = attacker;
    }

    public void setVictim(Player victim) {
        this.victim = victim;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Player getAttacker() {
        return this.attacker;
    }

    public Player getVictim() {
        return this.victim;
    }
}