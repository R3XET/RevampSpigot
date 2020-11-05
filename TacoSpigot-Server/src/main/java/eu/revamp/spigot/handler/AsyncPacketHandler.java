package eu.revamp.spigot.handler;

import net.minecraft.server.Packet;
import net.minecraft.server.PlayerConnection;

public interface AsyncPacketHandler {
    void onPacketReceive(PlayerConnection paramPlayerConnection, Packet paramPacket);

    void onPacketSent(PlayerConnection paramPlayerConnection, Packet paramPacket);
}

