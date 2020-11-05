package eu.revamp.spigot.netty;

import com.google.common.collect.Queues;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.Packet;

import java.util.Queue;

public class Spigot404Write {
	
	/*
	 * DONT NEED THIS CLASS SINCE WE FLUSH ON READ COMPLETE SO ITS AS FLUENT AS THIS HACK
	 */
	
    private static Queue<PacketQueue> queue = Queues.newConcurrentLinkedQueue();
    private static NettyTasks tasks = new NettyTasks();
    private Channel channel;
    
    public Spigot404Write(Channel channel) {
    	this.channel = channel;
    }
    
    private static class PacketQueue {
        private Packet item;
        private GenericFutureListener<? extends Future<? super Void>>[] listener;

        private PacketQueue(Packet item, GenericFutureListener<? extends Future<? super Void>>[] listener) {
            this.item = item;
            this.listener = listener;
        }
        
        public Packet getPacket() {
        	return item;
        }
        
        public GenericFutureListener<? extends Future<? super Void>>[] getListener() {
        	return listener;
        }
    }
    
    public static void writeThenFlush(Channel channel, Packet value, GenericFutureListener<? extends Future<? super Void>>[] listener) {
    	Spigot404Write writer = new Spigot404Write(channel);
        queue.add(new PacketQueue(value, listener));

        if (tasks.addTask()) {
            channel.pipeline().lastContext().executor().execute(writer::writeQueueAndFlush);
        }
    }
    
    public void writeQueueAndFlush() {
        // while clients submit us tasks
        while (tasks.fetchTask()) {
            // we write these tasks, but do not flush
            while (queue.size() > 0) {
                PacketQueue messages = queue.poll();
                if(messages != null) {
                    ChannelFuture future = channel.write(messages.getPacket());
                    if (messages.getListener() != null) {
                        future.addListeners(messages.getListener());
                    }
                    future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            }
        }
        channel.flush();
    }
}
