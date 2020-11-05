package eu.revamp.spigot.utils.generic.random;

import java.util.Arrays;
import java.util.Random;

public class FastRandom extends Random {
    protected long seed;

    public FastRandom() {
        this(System.nanoTime());
    }

    public FastRandom(long seed) {
        this.seed = seed;
    }

    public synchronized strictfp long getSeed() {
        return this.seed;
    }

    public synchronized strictfp void setSeed(long seed) {
        this.seed = seed;
        super.setSeed(seed);
    }

    public strictfp FastRandom clone() {
        return new FastRandom(getSeed());
    }

    protected strictfp int next(int nbits) {
        long x = this.seed;
        x ^= x << 21L;
        x ^= x >>> 35L;
        x ^= x << 4L;
        this.seed = x;
        x &= (1L << nbits) - 1L;
        return (int)x;
    }

    public synchronized strictfp void setSeed(int[] array) {
        if (array.length == 0)
            throw new IllegalArgumentException("Array length must be greater than zero");
        setSeed(Arrays.hashCode(array));
    }
}

