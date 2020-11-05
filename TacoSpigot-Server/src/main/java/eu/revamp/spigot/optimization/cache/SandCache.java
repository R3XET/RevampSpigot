package eu.revamp.spigot.optimization.cache;

public class SandCache {
    private boolean[] booleanCache;
    private double[] doubleCache;

    public SandCache(double[] doubles, boolean[] booleans) {
        this.doubleCache = doubles;
        this.booleanCache = booleans;
    }

    public double[] getDoubleCache() {
        return this.doubleCache;
    }

    public boolean[] getBooleanCache() {
        return this.booleanCache;
    }
}

