package eu.revamp.spigot.knockback;

public class KnockbackThread extends AbstractThread{

    public void run() {
        while (this.packets.size() > 0)
            this.packets.poll().run();
    }
}
