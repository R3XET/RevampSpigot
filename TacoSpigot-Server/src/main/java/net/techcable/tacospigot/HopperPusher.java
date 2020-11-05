package net.techcable.tacospigot;

import eu.revamp.spigot.config.Settings;
import net.jafama.FastMath;
import net.minecraft.server.*;

public interface HopperPusher {

    //TODO OLD CODE
    /*
    public default TileEntityHopper findHopper() {
        BlockPosition pos = new BlockPosition(getX(), getY(), getZ());
        int startX = pos.getX() - 1;
        int endX = pos.getX() + 1;
        int startY = Math.max(0, pos.getY() - 1);
        int endY = Math.min(255, pos.getY() + 1);
        int startZ = pos.getZ() - 1;
        int endZ = pos.getZ() + 1;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    BlockPosition adjacentPos = new BlockPosition(x, y, z);
                    TileEntityHopper hopper = HopperHelper.getHopper(getWorld(), adjacentPos);
                    if (hopper == null) continue; // Avoid playing with the bounding boxes, if at all possible
                    /**
                     * We add one to getY(), just like {@link TileEntityHopper#b(IHopper)}
                     *//*
                    AxisAlignedBB hopperBoundingBox = hopper.getHopperLookupBoundingBox();
                    if (hopperBoundingBox.b(this.getBoundingBox())) { // AxisAlignedBB.b(AxisAlignedBB) -> isIntersect()
                        return hopper;
                    }
                }
            }
        }
        return null;
    }
    */
    //TODO OLD CODE

    //TODO ADDED CODE
        default TileEntityHopper findHopper() {
            BlockPosition pos = new BlockPosition(getX(), getY(), getZ());
            if (Settings.IMP.SETTINGS.MISCELLANOUS.USE_MORE_EFFICIENT_HOPPER_SEARCH) {
                for (int y = pos.getY(); y > pos.getY() - 2; y--) {
                    BlockPosition newPos = new BlockPosition(pos.getX(), y, pos.getZ());
                    TileEntityHopper hopper = HopperHelper.getHopper(getWorld(), newPos);
                    if (hopper != null &&
                            getY() - y < 2.0D)
                        return hopper;
                }
            } else {
                int startX = pos.getX() - 1;
                int endX = pos.getX() + 1;

                //TODO OLD CODE
                //int startY = Math.max(0, pos.getY() - 1);
                //int endY = Math.min(255, pos.getY() + 1);
                //TODO OLD CODE

                //TODO ADDED CODE
                int startY = FastMath.max(0, pos.getY() - 1);
                int endY = FastMath.min(255, pos.getY() + 1);
                //TODO ADDED CODE
                int startZ = pos.getZ() - 1;
                int endZ = pos.getZ() + 1;
                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        for (int z = startZ; z <= endZ; z++) {
                            BlockPosition adjacentPos = new BlockPosition(x, y, z);
                            TileEntityHopper hopper = HopperHelper.getHopper(getWorld(), adjacentPos);
                            if (hopper != null) {
                                AxisAlignedBB hopperBoundingBox = hopper.getHopperLookupBoundingBox();
                                if (hopperBoundingBox.b(getBoundingBox()))
                                    return hopper;
                            }
                        }
                    }
                }
            }
            return null;
        }
    //TODO ADDED CODE

    public boolean acceptItem(TileEntityHopper hopper);

    public default boolean tryPutInHopper() {
        if (!getWorld().tacoSpigotConfig.isHopperPushBased) return false;
        TileEntityHopper hopper = findHopper();
        return hopper != null && hopper.canAcceptItems() && acceptItem(hopper);
    }

    public AxisAlignedBB getBoundingBox();

    public World getWorld();

    // Default implementations for entities

    public default double getX() {
        return ((Entity) this).locX;
    }

    public default double getY() {
        return ((Entity) this).locY;
    }

    public default double getZ() {
        return ((Entity) this).locZ;
    }

}
