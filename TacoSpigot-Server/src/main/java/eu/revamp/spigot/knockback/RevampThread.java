package eu.revamp.spigot.knockback;

import eu.revamp.spigot.RevampSpigot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RevampThread {
    private final ExecutorService service;

    private AbstractThread asyncHitDetection;

    private AbstractThread asyncKnockbackDetection;

    public RevampThread(int threads) {
        this.service = Executors.newFixedThreadPool(threads);
    }

    public void requestTask(Runnable runnable) {
        this.service.submit(runnable);
    }

    public void loadAsyncThreads() {
        try {
            this.asyncHitDetection = new HitsThread();
            this.asyncKnockbackDetection = new KnockbackThread();
        } catch (Exception ex) {
            RevampSpigot.getInstance().getLogger().warning("Could not load async threads! Please set async-hit-detection and async-knockback in config.yml to false! {0}", ex.getMessage());
        }
    }

    public AbstractThread getAsyncHitDetection() {
        return this.asyncHitDetection;
    }

    public AbstractThread getAsyncKnockbackDetection() {
        return this.asyncKnockbackDetection;
    }
}
