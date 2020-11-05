package eu.revamp.spigot.events.player.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class PlayerDigBlockEvent extends BlockEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public PlayerDigBlockEvent(Player player, Block block) {
        super(block);
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
}