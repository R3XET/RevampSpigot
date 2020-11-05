package net.minecraft.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class InboundDiscardHandler extends ChannelInboundHandlerAdapter {
    public static final String DISCARD_FIRST = "I_DISCARD_FIRST";

    public static final String DISCARD = "I_DISCARD";

    public static InboundDiscardHandler INSTANCE = new InboundDiscardHandler();

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ((ByteBuf)msg).release();
            ctx.close();
        }
    }
}

