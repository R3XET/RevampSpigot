package eu.revamp.spigot.optimization.cache;

public class TNTCache {
    private double[] doubleCache;
    private boolean[] booleanCache;

    public TNTCache(double[] a, boolean[] a0) {
        this.doubleCache = a;
        this.booleanCache = a0;
    }

    public double[] getDoubleCache() {
        return this.doubleCache;
    }

    public boolean[] getBooleanCache() {
        return this.booleanCache;
    }
}
