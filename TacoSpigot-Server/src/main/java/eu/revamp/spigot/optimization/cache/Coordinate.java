package eu.revamp.spigot.optimization.cache;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Coordinate {
    public double x;
    public double y;
    public double z;


    public Coordinate(double x2, double y2, double z2) {
        this.x = x2;
        this.y = y2;
        this.z = z2;
    }
}
