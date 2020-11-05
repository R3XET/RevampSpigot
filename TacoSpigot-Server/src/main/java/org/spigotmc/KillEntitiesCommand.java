package org.spigotmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class KillEntitiesCommand extends Command {
    public KillEntitiesCommand(String name) {
        super(name);
        this.description = "Kill Entities";
        this.usageMessage = "§e/killentities";
        this.setPermission("bukkit.command.killentities");
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender))
            return true;
        int i = 0;
        List<World> immutableWorlds = Bukkit.getWorlds();
        for (World world : immutableWorlds) {
            List<Entity> immutableEntities = new ArrayList<>(world.getEntities());
            for (Entity entity : immutableEntities) {
                EntityType entityType = entity.getType();
                if (shouldBeRemoved(entityType)) {
                    entity.remove();
                    i++;
                }
            }
        }
        sender.sendMessage(ChatColor.GREEN + "You have removed a total of " + ChatColor.GRAY + i + ChatColor.GREEN + " entities");
        return false;
    }

    private boolean shouldBeRemoved(EntityType entityType) {
        return !(!entityType.equals(EntityType.DROPPED_ITEM) && !entityType.equals(EntityType.PRIMED_TNT) && !entityType.equals(
                EntityType.SKELETON) && !entityType.equals(
                EntityType.ZOMBIE) && !entityType.equals(
                EntityType.COW) && !entityType.equals(
                EntityType.SHEEP) && !entityType.equals(
                EntityType.ENDERMAN) && !entityType.equals(
                EntityType.PIG_ZOMBIE) && !entityType.equals(
                EntityType.PIG) && !entityType.equals(
                EntityType.CREEPER) && !entityType.equals(
                EntityType.BAT) && !entityType.equals(
                EntityType.CHICKEN) && !entityType.equals(
                EntityType.CAVE_SPIDER) && !entityType.equals(
                EntityType.SPIDER));
    }
}
