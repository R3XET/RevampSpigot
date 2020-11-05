package eu.revamp.spigot.events.scoreboard;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUpdateEvent extends Event implements Cancellable {
    private final HandlerList handlers = new HandlerList();

    String entry;

    Scoreboard scoreboard;

    private boolean cancelled = false;

    public ScoreboardUpdateEvent(String entry, Scoreboard scoreboard) {
        this.entry = entry;
        this.scoreboard = scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return this.handlers;
    }

    public String getEntry() {
        return this.entry;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
