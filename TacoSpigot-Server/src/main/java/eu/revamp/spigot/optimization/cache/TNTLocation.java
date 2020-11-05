package eu.revamp.spigot.optimization.cache;

public class TNTLocation {
    private double posX;
    private double posY;
    private double posZ;
    private double motX;
    private double motY;
    private double motZ;
    private long hashCode;

    public TNTLocation(net.minecraft.server.EntityTNTPrimed a) {
        this.posX = a.locX;
        this.posY = a.locY;
        this.posZ = a.locZ;
        this.motX = a.motX;
        this.motY = a.motY;
        this.motZ = a.motZ;
        this.hashCode = 31L * (31L * (31L * (31L * (31L * (217L + Double.doubleToLongBits(this.posX)) + Double.doubleToLongBits(this.posY)) + Double.doubleToLongBits(this.posZ)) + Double.doubleToLongBits(this.motX)) + Double.doubleToLongBits(this.motY)) + Double.doubleToLongBits(this.motZ);
    }

    public boolean equals(Object a) {
        TNTLocation a0 = (TNTLocation)a;
        return this.posX == a0.posX && this.posY == a0.posY && this.posZ == a0.posZ && this.motX == a0.motX && this.motY == a0.motY && this.motZ == a0.motZ;
    }

    public int hashCode() {
        return (int)this.hashCode;
    }
}

