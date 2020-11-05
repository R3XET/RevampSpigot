package eu.revamp.spigot.utils.date;

public enum TimeUnit
{
    MILLISECOND("MILLISECOND", 0, 1L),
    SECOND("SECOND", 1, 1000L),
    MINUTE("MINUTE", 2, 60000L),
    HOUR("HOUR", 3, 3600000L);

    private long time;

    TimeUnit(String s, int n, long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }
}