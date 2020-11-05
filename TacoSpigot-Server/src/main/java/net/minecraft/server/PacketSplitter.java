package net.minecraft.server;

import eu.revamp.spigot.config.Settings;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    private final int MIN_LENGTH_FIRST_PACKET = Settings.IMP.SETTINGS.ANTICRASH.MIN_FIRST_LOGIN_PACKET_LENGTH;
    private final int MAX_LENGTH_FIRST_PACKET = Settings.IMP.SETTINGS.ANTICRASH.MAX_FIRST_LOGIN_PACKET_LENGTH;

    private final int MIN_LENGTH_SECOND_PACKET = Settings.IMP.SETTINGS.ANTICRASH.MIN_SECOND_LOGIN_PACKET_LENGTH;
    private final int MAX_LENGTH_SECOND_PACKET = Settings.IMP.SETTINGS.ANTICRASH.MAX_SECOND_LOGIN_PACKET_LENGTH;
    private boolean first = true;
    private boolean second = false;
    private boolean stop = false;


    protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) {
        paramByteBuf.markReaderIndex();
        byte[] arrayOfByte = new byte[3];
        for (byte b = 0; b < arrayOfByte.length; b++) {
            if (!paramByteBuf.isReadable()) {
                paramByteBuf.resetReaderIndex();
                return;
            }
            arrayOfByte[b] = paramByteBuf.readByte();
            if (arrayOfByte[b] >= 0) {
                PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(arrayOfByte));
                try {
                    int i = packetDataSerializer.e();
                    if (paramByteBuf.readableBytes() < i) {
                        paramByteBuf.resetReaderIndex();
                        return;
                    }
                    paramList.add(paramByteBuf.readBytes(i));
                    return;
                } finally {
                    packetDataSerializer.release();
                }
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}


//TODO OLD CODE ANTICRASH
    /*
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (this.stop) {
            ctx.close();
            //System.out.println("test 1");
            return;
        }
        in.markReaderIndex();
        byte[] buf = new byte[3];
        for (byte b = 0; b < buf.length; b++) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                //System.out.println("test 2");
                return;
            }
            buf[b] = in.readByte();
            if (buf[b] >= 0) {
                PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(buf));


                //ADDED
                int length = PacketDataSerializer.readVarIntLengthSpecial(buf);

                //TODO TO GET PACKET LENGTH
                //System.out.println("Lunghezza --> " + length);

                if (length <= 0) {
                    this.stop = true;
                    //System.out.println("Lunghezza minore o uguale a 0 --> " + length);
                    InetAddress inetAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
                    setSingleDecode(true);
                    //System.out.println("test 4");
                    ctx.close();
                    return;
                }
                if (this.first) {
                    //System.out.println("test 3");
                    if (length < MIN_LENGTH_FIRST_PACKET || length > MAX_LENGTH_FIRST_PACKET) {
                        InetAddress inetAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
                        String str = inetAddress.getHostAddress();
                        if (!str.equals("127.0.0.1") && !str.equals("0.0.0.0")) {
                            setSingleDecode(true);
                            ctx.close();
                            this.stop = true;
                            //System.out.println("test 6");
                            if (Settings.IMP.SETTINGS.ANTICRASH.INFORM_CONSOLE_IF_EXCEED_LOGIN_PACKET_LENGTH_LIMIT) {
                                RevampSpigot.getInstance().getLogger().info("(Login) -> Blocking " + str + " Invalid login packet! Min: " + MIN_LENGTH_FIRST_PACKET + ", max: " + MAX_LENGTH_FIRST_PACKET + ", current: " + length);
                            }
                            return;
                        }
                    }
                } else if (this.second) {
                    //System.out.println("test 33");
                    this.second = false;
                    if (length != 1 && (length < MIN_LENGTH_SECOND_PACKET || length > MAX_LENGTH_SECOND_PACKET)) {
                        InetAddress inetAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
                        String str = inetAddress.getHostAddress();
                        if (!str.equals("127.0.0.1")) {
                            this.stop = true;
                            setSingleDecode(true);
                            ctx.close();
                            //System.out.println("test 7");
                            if (Settings.IMP.SETTINGS.ANTICRASH.INFORM_CONSOLE_IF_EXCEED_LOGIN_PACKET_LENGTH_LIMIT) {
                                RevampSpigot.getInstance().getLogger().info("(Login) -> Blocking " + str + " Invalid login packet v2! Min: " + MIN_LENGTH_SECOND_PACKET + ", max: " + MAX_LENGTH_SECOND_PACKET + ", current: " + length);
                            }
                            return;
                        }
                    }
                }
                if (in.readableBytes() < length) {
                    in.resetReaderIndex();
                    //System.out.println("Lunghezza 5 --> " + length);
                    //System.out.println("test 5");
                    return;
                }
                if (this.first) {
                    this.first = false;
                    this.second = true;
                }
                /*
                if (in.hasMemoryAddress()) {
                    out.add(in.slice(in.readerIndex(), length).retain());
                    in.skipBytes(length);
                    System.out.println("Lunghezza 9 --> " + length);
                    System.out.println("test 9");
                } else {
                    ByteBuf dst = ctx.alloc().directBuffer(length);
                    in.readBytes(dst);
                    out.add(dst);
                    System.out.println("Lunghezza 8 --> " + length);
                    System.out.println("test 8");
                }*/
/*
                //END ADD
                try {
                    int i = packetDataSerializer.e();
                    if (in.readableBytes() < i) {
                        in.resetReaderIndex();
                        return;
                    }
                    out.add(in.readBytes(i));
                    return;
                } finally {
                    packetDataSerializer.release();
                }
            }
        }
        InetAddress address = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
        String hostname = address.getHostAddress();
        ctx.close();
        if (Settings.IMP.SETTINGS.ANTICRASH.INFORM_CONSOLE_IF_EXCEED_LOGIN_PACKET_LENGTH_LIMIT) {
            RevampSpigot.getInstance().getLogger().info("(Login) -> Blocking " + hostname + " -> Length wider than 21 bit!");
        }
        //throw new CorruptedFrameException("length wider than 21-bit");
    }*/
    //TOOD OLD CODE ANTICRASH


/*
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        if (this.stop) {
            ctx.close();
            System.out.println("test 1");
            return;
        }
        in.markReaderIndex();
        byte[] buf = new byte[3];
        int i = 0;
        while (i < buf.length) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                System.out.println("test 2");
                return;
            }
            buf[i] = in.readByte();
            if (in.readByte() >= 0) {
                int length = PacketDataSerializer.readVarIntLengthSpecial(buf);
                System.out.println("Lunghezza boh --> " + length);
                if (length <= 0) {
                    this.stop = true;
                    System.out.println("Lunghezza minore o uguale a 0 --> " + length);
                    InetAddress inetAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
                    setSingleDecode(true);
                    System.out.println("test 4");
                    ctx.close();
                    return;
                }
                if (this.first) {
                    System.out.println("test 3");
                    if (length < MIN_LENGTH_FIRST_PACKET || length > MAX_LENGTH_FIRST_PACKET) {
                        InetAddress inetAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
                        String str = inetAddress.getHostAddress();
                        if (!str.equals("127.0.0.1") && !str.equals("0.0.0.0")) {
                            setSingleDecode(true);
                            ctx.close();
                            this.stop = true;
                            System.out.println("test 6");
                            if (RevampSpigot.getInstance().isAntiCrashInformConsoleIfExceedLoginPacketLengthLimit()) {
                                RevampSpigot.getInstance().getLogger().info("(Login) -> Blocking " + str + " Invalid login packet! Min: " + MIN_LENGTH_FIRST_PACKET + ", max: " + MAX_LENGTH_FIRST_PACKET + ", current: " + length);
                            }
                            return;
                        }
                    }
                } else if (this.second) {
                    System.out.println("test 33");
                    this.second = false;
                    if (length != 1 && (length < MIN_LENGTH_SECOND_PACKET || length > MAX_LENGTH_SECOND_PACKET)) {
                        InetAddress inetAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
                        String str = inetAddress.getHostAddress();
                        if (!str.equals("127.0.0.1")) {
                            this.stop = true;
                            setSingleDecode(true);
                            ctx.close();
                            System.out.println("test 7");
                            if (RevampSpigot.getInstance().isAntiCrashInformConsoleIfExceedLoginPacketLengthLimit()) {
                                RevampSpigot.getInstance().getLogger().info("(Login) -> Blocking " + str + " Invalid login packet v2! Min: " + MIN_LENGTH_SECOND_PACKET + ", max: " + MAX_LENGTH_SECOND_PACKET + ", current: " + length);
                            }
                            return;
                        }
                    }
                }
                if (in.readableBytes() < length) {
                    in.resetReaderIndex();
                    System.out.println("Lunghezza 5 --> " + length);
                    System.out.println("test 5");
                    return;
                }
                if (this.first) {
                    this.first = false;
                    this.second = true;
                }
                if (in.hasMemoryAddress()) {
                    out.add(in.slice(in.readerIndex(), length).retain());
                    in.skipBytes(length);
                    System.out.println("Lunghezza 9 --> " + length);
                    System.out.println("test 9");
                } else {
                    ByteBuf dst = ctx.alloc().directBuffer(length);
                    in.readBytes(dst);
                    out.add(dst);
                    System.out.println("Lunghezza 8 --> " + length);
                    System.out.println("test 8");
                }
                return;
            }
            i++;
        }
        System.out.println("test 10");
        setSingleDecode(true);
        this.stop = true;
        ctx.pipeline().addFirst("I_DISCARD_FIRST", InboundDiscardHandler.INSTANCE).addAfter(ctx.name(), "I_DISCARD", InboundDiscardHandler.INSTANCE);
        InetAddress address = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress();
        String hostname = address.getHostAddress();
        ctx.close();
        if (RevampSpigot.getInstance().isAntiCrashInformConsoleIfExceedLoginPacketLengthLimit()) {
            RevampSpigot.getInstance().getLogger().info("(Login) -> Blocking " + hostname + " -> Length wider than 21 bit!");
        }
    }
}*/

