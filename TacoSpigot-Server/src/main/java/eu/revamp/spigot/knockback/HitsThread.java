package eu.revamp.spigot.knockback;

public class HitsThread extends AbstractThread {

    public void run() {
        while (this.packets.size() > 0)
            this.packets.poll().run();
    }
}

