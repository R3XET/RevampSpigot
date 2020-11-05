package eu.revamp.spigot.handler;

import net.minecraft.server.PacketPlayInFlying;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface AsyncMovementHandler {
    void onLocationUpdate(Player paramPlayer, Location paramLocation1, Location paramLocation2, PacketPlayInFlying paramPacketPlayInFlying);

    void onRotationUpdate(Player paramPlayer, Location paramLocation1, Location paramLocation2, PacketPlayInFlying paramPacketPlayInFlying);
}
