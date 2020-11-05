package eu.revamp.spigot.utils.time;

import java.text.DecimalFormat;


public class Cooldown {

    private long start = System.currentTimeMillis();
    private long expire;
    private static DecimalFormat SECONDS_FORMAT = new DecimalFormat("#0.0");
    private String name;

    public Cooldown(int seconds, String name) {
        long duration = 1000 * seconds;
        this.expire = this.start + duration;
        this.name = name;
    }

    public long getPassed() {
        return System.currentTimeMillis() - this.start;
    }

    public long getRemaining() {
        return this.expire - System.currentTimeMillis();
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - this.expire >= 1;
    }

    public int getSecondsLeft() {
        return (int) getRemaining() / 1000;
    }

    public String getMiliSecondsLeft() {
        return formatSeconds(this.getRemaining());
    }

    public void cancelCountdown() {
        this.expire = 0;
    }

    private static String formatSeconds(long time) {
        return SECONDS_FORMAT.format(time / 1000.0F);
    }

    public long getStart() {
        return this.start;
    }

    public long getExpire() {
        return this.expire;
    }

    public String getName() {
        return this.name;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public void setName(String name) {
        this.name = name;
    }
}
