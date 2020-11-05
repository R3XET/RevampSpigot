package eu.revamp.spigot.utils.entity;

import org.bukkit.entity.Entity;

public class EntityUtils {
    public static String getEntityName(Entity entity) {
        switch (entity.getType().name()) {
            case "VILLAGER": {
                return "Villager";
            }
            case "PLAYER": {
                return "Player";
            }
            case "SILVERFISH": {
                return "Silverfish";
            }
            case "SPIDER": {
                return "Spider";
            }
            case "ENDERMAN": {
                return "Enderman";
            }
            case "WITHER": {
                return "Wither";
            }
            case "ZOMBIE": {
                return "Zombie";
            }
            case "SKELETON": {
                return "Skeleton";
            }
            case "PIG_ZOMBIE": {
                return "Pig Zombie";
            }
            case "IRON_GOLEM": {
                return "Iron Golem";
            }
            case "WOLF": {
                return "Wolf";
            }
            case "CAVE_SPIDER": {
                return "Cave Spider";
            }
            case "BLAZE": {
                return "Blaze";
            }
            case "SLIME": {
                return "Slime";
            }
            case "WITCH": {
                return "Witch";
            }
            case "MAGMA_CUBE": {
                return "Magma Cube";
            }
            case "CREEPER": {
                return "Creeper";
            }
            default:
                break;
        }
        return "";
    }
}
