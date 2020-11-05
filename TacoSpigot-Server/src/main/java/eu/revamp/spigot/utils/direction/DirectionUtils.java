package eu.revamp.spigot.utils.direction;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class DirectionUtils {

    private static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

    public static String faceToString(BlockFace face) {
        switch (face) {
            case NORTH:
                return "N";
            case NORTH_EAST:
                return "NE";
            case EAST:
                return "E";
            case SOUTH_EAST:
                return "SE";
            case SOUTH:
                return "S";
            case SOUTH_WEST:
                return "SW";
            case WEST:
                return "W";
            case NORTH_WEST:
                return "NW";
        }
        return null;
    }
    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return "WEST";
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return "NORTH-WEST";
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return "NORTH";
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return "NORTH-EAST";
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return "EAST";
        }
        if ((202.5D <= rotation) && (rotation < 247.5D)) {
            return "SOUTH-EAST";
        }
        if ((247.5D <= rotation) && (rotation < 292.5D)) {
            return "SOUTH";
        }
        if ((292.5D <= rotation) && (rotation < 337.5D)) {
            return "SOUTH-WEST";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "SOUTH";
        }
        return null;
    }
    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections)
            return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();

        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }
}
