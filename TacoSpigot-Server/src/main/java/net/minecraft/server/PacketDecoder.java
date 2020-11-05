package net.minecraft.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    private static final Logger a = LogManager.getLogger();

    private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b);

    private final EnumProtocolDirection c;

    private boolean disconnected;

    public PacketDecoder(EnumProtocolDirection enumprotocoldirection) {
        this.c = enumprotocoldirection;
    }

    protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
        if (var2.readableBytes() != 0) {
            PacketDataSerializer var4 = new PacketDataSerializer(var2);
            int var5 = var4.e();
            Packet var6 = var1.channel().attr(NetworkManager.c).get().a(this.c, var5);
            if (var6 == null) {
                throw new IOException("Bad packet id " + var5);
            } else {
                var6.a(var4);
                if (var4.readableBytes() > 0) {
                    throw new IOException("Packet " + var1.channel().attr(NetworkManager.c).get().a() + "/" + var5 + " (" + var6.getClass().getSimpleName() + ") was larger than I expected, found " + var4.readableBytes() + " bytes extra whilst reading packet " + var5);
                } else {
                    var3.add(var6);
                    if (a.isDebugEnabled()) {
                        a.debug(b, " IN: [{}:{}] {}", var1.channel().attr(NetworkManager.c).get(), var5, var6.getClass().getName());
                    }

                }
            }
        }
    }

    //TODO OLD CODE
    /*
    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List<Object> list) throws Exception {
        if (this.disconnected)
            return;
        if (bytebuf.readableBytes() != 0) {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf);
            int i = packetdataserializer.e();
            Packet<?> packet = channelhandlercontext.channel().attr(NetworkManager.c).get().a(this.c,
                    i);
            if (Settings.IMP.PACKET_LIMITER.BYTEBUF_LIMITATION && bytebuf.readableBytes() != 0 && bytebuf.capacity() > Settings.IMP.PACKET_LIMITER.BYTEBUF_LIMIT) {
                if (bytebuf.refCnt() > 0)
                    bytebuf.clear();
                this.disconnected = true;
                channelhandlercontext.close();
                RevampSpigot.getInstance().getLogger().info("{0} tried to crash server probably! ByteBuf > 5000", channelhandlercontext.toString());
                Bukkit.getPluginManager().callEvent(new ExploitDetectedEvent(channelhandlercontext.toString(), channelhandlercontext.channel().remoteAddress().toString(), "Too big packet size, packet: " + ((packet == null) ? "unknown" : packet.getClass().getSimpleName())));
                return;
            }
            if (packet == null)
                throw new IOException("Bad packet id " + i);
            packet.a(packetdataserializer);
            if (packetdataserializer.readableBytes() > 0)
                throw new IOException("Packet " + channelhandlercontext.channel().attr(NetworkManager.c).get().a() + "/" +
                        i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " +
                        packetdataserializer.readableBytes() + " bytes extra whilst reading packet " + i);
            list.add(packet);
            if (a.isDebugEnabled())
                a.debug(b, " IN: [{}:{}] {}",
                        channelhandlercontext.channel().attr(NetworkManager.c).get(),
                        i, packet.getClass().getName());
        }
    }*/
    //TODO OLD CODE
}
