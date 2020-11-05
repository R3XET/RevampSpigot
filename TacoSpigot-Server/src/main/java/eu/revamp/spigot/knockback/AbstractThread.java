package eu.revamp.spigot.knockback;

import eu.revamp.spigot.config.Settings;
import eu.revamp.spigot.netty.Spigot404Write;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractThread {
    private boolean running;

    private int TICK_TIME = 1000000000 / Settings.IMP.SETTINGS.DEVELOPER_SETTINGS.THREAD_TPS;
    //private int TICK_TIME;

    private Thread t;

    protected Queue<Runnable> packets;

    public AbstractThread() {
        this.running = false;
        this.packets = new ConcurrentLinkedQueue<>();
        this.running = true;
        //this.TICK_TIME = 16666666;
        if (Settings.IMP.SETTINGS.ASYNC.AFFINITY_THREADS) {
            try {
                Exception exception2, exception1 = null;
            } catch (Exception exception) {
                this.t = new Thread(this::loop);
                this.t.start();
            }
        } else {
            this.t = new Thread(this::loop);
            this.t.start();
        }
    }

    public void loop() {
        long lastTick = System.nanoTime();
        long catchupTime = 0L;
        while (this.running) {
            long curTime = System.nanoTime();
            long wait = this.TICK_TIME - curTime - lastTick - catchupTime;
            if (wait > 0L) {
                try {
                    Thread.sleep(wait / 1000000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catchupTime = 0L;
                continue;
            }
            catchupTime = Math.min(1000000000L, Math.abs(wait));
            run();
            lastTick = curTime;
        }
    }

    public void addPacket(Packet packet, NetworkManager manager, GenericFutureListener[] agenericfuturelistener) {
        this.packets.add(() -> Spigot404Write.writeThenFlush(manager.channel, packet, agenericfuturelistener));
    }

    public Thread getThread() {
        return this.t;
    }

    public abstract void run();
}