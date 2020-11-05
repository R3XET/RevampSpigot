package net.minecraft.server;

import eu.revamp.spigot.config.Settings;
import net.jafama.FastMath;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Explosive;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityTNTPrimed extends Entity {

    public int fuseTicks;
    private EntityLiving source;
    public float yield = 4; // CraftBukkit - add field
    public boolean isIncendiary = false; // CraftBukkit - add field
    public org.bukkit.Location sourceLoc; // PaperSpigot

    //TODO ADDED CODE
    private int distanceX;

    private int distanceZ;

    public int id;

    public int amount;
    //TODO ADDED CODE

    // PaperSpigot start - TNT source location API
    public EntityTNTPrimed(World world) {
        this(null, world);
    }

    public EntityTNTPrimed(org.bukkit.Location loc, World world) {
        super(world);
        sourceLoc = loc;
    // PaperSpigot end
        this.k = true;
        this.setSize(0.98F, 0.98F);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedTNTEntities; // PaperSpigot
        //TODO ADDED CODE
        this.multiplier = 1;
        //TODO ADDED CODE
    }

    //TODO ADDED CODE
    public EntityTNTPrimed(org.bukkit.Location loc, World world, boolean skipVelocityMultiplier) {
        super(world, true, false);
        this.yield = 4.0F;
        this.isIncendiary = false;
        this.sourceLoc = loc;
        this.k = true;
        this.setSize(0.98F, 0.98F);
        this.loadChunks = world.paperSpigotConfig.loadUnloadedTNTEntities;
        this.multiplier = 1;
        this.skipVelocityMultiplier = skipVelocityMultiplier;

        //TODO ADDED CODE
        if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.DISABLE_LEFT_SHOOTING) this.distanceX = this.distanceZ = 0;
        this.amount = 1;
        this.id = world.id++;
        //TODO ADDED CODE

    }
    //TODO ADDED CODE


    public EntityTNTPrimed(org.bukkit.Location loc, World world, double d0, double d1, double d2, EntityLiving entityliving) {
        this(loc, world);
        this.setPosition(d0, d1, d2);

        //TODO OLD CODE
        /*
        float f = (float) (Math.random() * 3.1415927410125732D * 2.0D);
        this.motX = -((float) Math.sin(f)) * 0.02F;
        this.motY = 0.20000000298023224D;
        this.motZ = -((float) Math.cos(f)) * 0.02F;
         */
        //TODO OLD CODE

        //TODO ADDED CODE
        float f = (float)(FastMath.random() * 3.1415927410125732D * 2.0D);
        this.motX = (-((float)FastMath.sin(f)) * 0.02F);
        this.motY = 0.20000000298023224D;
        this.motZ = (-((float)FastMath.cos(f)) * 0.02F);
        //TODO ADDED CODE

        this.fuseTicks = 80;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
        this.source = entityliving;
        //TODO ADDED CODE
        this.multiplier = 1;
        this.skipVelocityMultiplier = false;
        //TODO ADDED CODE

        //TODO ADDED CODE
        if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.DISABLE_LEFT_SHOOTING) this.distanceX = this.distanceZ = 0;
        this.amount = 1;
        this.id = world.id++;
        //TODO ADDED CODE

        if (world.paperSpigotConfig.fixCannons) this.motX = this.motZ = 0.0F; // PaperSpigot - Fix cannons
    }

    protected void h() {}

    protected boolean s_() {
        return false;
    }

    public boolean ad() {
        return !this.dead;
    }


    //TODO OLD CODE
    /*
    public void t_() {
        if (world.spigotConfig.currentPrimedTnt++ > world.spigotConfig.maxTntTicksPerTick) { return; } // Spigot
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033D;
        this.move(this.motX, this.motY, this.motZ);

        // PaperSpigot start - Drop TNT entities above the specified height
        if (this.world.paperSpigotConfig.tntEntityHeightNerf != 0 && this.locY > this.world.paperSpigotConfig.tntEntityHeightNerf) {
            this.die();
        }
        // PaperSpigot end

        // PaperSpigot start - Remove entities in unloaded chunks
        if (this.inUnloadedChunk && world.paperSpigotConfig.removeUnloadedTNTEntities) {
            this.die();
            this.fuseTicks = 2;
        }
        // PaperSpigot end

        this.motX *= 0.9800000190734863D;
        this.motY *= 0.9800000190734863D;
        this.motZ *= 0.9800000190734863D;
        if (this.onGround) {
            this.motX *= 0.699999988079071D;
            this.motZ *= 0.699999988079071D;
            this.motY *= -0.5D;
        }

        if (this.fuseTicks-- <= 0) {
            // CraftBukkit start - Need to reverse the order of the explosion and the entity death so we have a location for the event
            // this.die();
            if (!this.world.isClientSide) {
                this.explode();
            }
            this.die();
            // CraftBukkit end
        } else {
            this.W();
            this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
        }

    }
    */
    //TODO OLD CODE

    //TODO ADDED CODE
    public void t_() {
        if (!Settings.IMP.SETTINGS.PERFORMANCE.TNT.FORCE_DISABLE_TNT_LIMIT && this.world.spigotConfig.currentPrimedTnt > this.world.spigotConfig.maxTntTicksPerTick)
            return;
        this.world.spigotConfig.currentPrimedTnt++;
        if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.DISABLE_LEFT_SHOOTING)
            if (this.distanceX >= Settings.IMP.SETTINGS.PERFORMANCE.TNT.LEFT_SHOOTING_AXEIS_LOCK_DISTANCE) {
                this.motZ = 0.0D;
            } else if (this.distanceZ >= Settings.IMP.SETTINGS.PERFORMANCE.TNT.LEFT_SHOOTING_AXEIS_LOCK_DISTANCE) {
                this.motX = 0.0D;
            } else {
                this.distanceX = (int)(this.distanceX + Math.abs(this.locX - this.lastX));
                this.distanceZ = (int)(this.distanceZ + Math.abs(this.locZ - this.lastZ));
            }
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033D;
        if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.TNT_MERGE && this.fuseTicks <= 0 && this.amount > 1) {
            EntityTNTPrimed tnt = new EntityTNTPrimed(new Location(this.world.getWorld(), this.locX, this.locY, this.locZ), this.world);
            tnt.source = this.source;
            tnt.amount = 1;
            tnt.id = this.id;
            this.world.addEntity(tnt);
            int amount = this.amount;
            int iteration = 0;
            boolean merge = false;
            for (int i = 0; i < amount &&
                    !this.dead; i++, this.amount--) {
                tnt.boundingBox = new AxisAlignedBB(getBoundingBox());
                tnt.locX = this.locX;
                tnt.locY = this.locY;
                tnt.locZ = this.locZ;
                tnt.lastX = this.lastX;
                tnt.lastY = this.lastY;
                tnt.lastZ = this.lastZ;
                tnt.motX = this.motX;
                tnt.motY = this.motY;
                tnt.motZ = this.motZ;
                tnt.move(tnt.motX, tnt.motY, tnt.motZ);
                this.world.movement++;
                if (this.motX == 0.0D && tnt.motY == 0.0D && this.motY == -0.03999999910593033D && this.motZ == 0.0D && ++iteration == 2 && merge) {
                    tnt.amount = amount - i;
                    i = amount;
                }
                Explosion explosion = tnt.explode();
                if (iteration == 1 && (explosion == null || explosion.inWater || explosion.wasCanceled))
                    merge = true;
            }
            tnt.die();
            die();
            return;
        }
        move(this.motX, this.motY, this.motZ);
        this.world.movement++;
        if (this.world.paperSpigotConfig.tntEntityHeightNerf != 0 && this.locY > this.world.paperSpigotConfig.tntEntityHeightNerf)
            die();
        if (this.inUnloadedChunk && this.world.paperSpigotConfig.removeUnloadedTNTEntities) {
            die();
            this.fuseTicks = 2;
        }
        this.motX *= 0.9800000190734863D;
        this.motY *= 0.9800000190734863D;
        this.motZ *= 0.9800000190734863D;
        if (this.onGround) {
            this.motX *= 0.699999988079071D;
            this.motZ *= 0.699999988079071D;
            this.motY *= -0.5D;
        }
        if (this.fuseTicks <= 0) {
            if (!this.world.isClientSide)
                explode();
            die();
        } else {
            this.fuseTicks--;
            W();
            if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.SHOW_TNT_PARTICLES)
                this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
        }
    }

    //TODO ADDED CODE


    //TODO OLD
    /*
    private void explode() {
        // CraftBukkit start
        // float f = 4.0F;

        // PaperSpigot start - Force load chunks during TNT explosions
        ChunkProviderServer chunkProviderServer = ((ChunkProviderServer) world.chunkProvider);
        boolean forceChunkLoad = chunkProviderServer.forceChunkLoad;
        if (world.paperSpigotConfig.loadUnloadedTNTEntities) {
            chunkProviderServer.forceChunkLoad = true;
        }
        // PaperSpigot end

        org.bukkit.craftbukkit.CraftServer server = this.world.getServer();

        ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Explosive) org.bukkit.craftbukkit.entity.CraftEntity.getEntity(server, this));
        server.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            this.world.createExplosion(this, this.locX, this.locY + (double) (this.length / 2.0F), this.locZ, event.getRadius(), event.getFire(), true);
        }
        // CraftBukkit end

        // PaperSpigot start - Force load chunks during TNT explosions
        if (world.paperSpigotConfig.loadUnloadedTNTEntities) {
            chunkProviderServer.forceChunkLoad = forceChunkLoad;
        }
        // PaperSpigot end
    }
    */
    //TODO OLD
    //TODO ADDED CODE
    private Explosion explode() {
        ChunkProviderServer chunkProviderServer = (ChunkProviderServer)this.world.chunkProvider;
        boolean forceChunkLoad = chunkProviderServer.forceChunkLoad;
        if (this.world.paperSpigotConfig.loadUnloadedTNTEntities)
            chunkProviderServer.forceChunkLoad = true;
        CraftServer server = this.world.getServer();
        ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive) CraftEntity.getEntity(server, this));
        server.getPluginManager().callEvent(event);
        Explosion explosion = null;
        if (!event.isCancelled())
            explosion = this.world.createExplosion(this, this.locX, this.locY + (this.length / 2.0F), this.locZ, event.getRadius(), event.getFire(), true);
        if (this.world.paperSpigotConfig.loadUnloadedTNTEntities)
            chunkProviderServer.forceChunkLoad = forceChunkLoad;
        return explosion;
    }
    //TODO ADDED CODE



    //TODO ADDED CODE
    public void move(double d0, double d1, double d2) {
        try {
            checkBlockCollisions();
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");
            appendEntityCrashDetails(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
        if (d0 == 0.0D && d1 == 0.0D && d2 == 0.0D && this.vehicle == null && this.passenger == null)
            return;
        if (this.H) {
            this.H = false;
            d0 *= 0.25D;
            d1 *= 0.05000000074505806D;
            d2 *= 0.25D;
            this.motX = 0.0D;
            this.motY = 0.0D;
            this.motZ = 0.0D;
        }
        double d6 = d0;
        double d7 = d1;
        double d8 = d2;
        AxisAlignedBB spaceTraveled = this.boundingBox.a(d0, d1, d2);
        boolean axisScan = (spaceTraveled.volume() > 10.0D);
        World.MovingCannoningEntity entity = null;
        World.MovementData data = null;
        if (axisScan) {
            entity = new World.MovingCannoningEntity(this);
            data = this.world.movementCache.get(entity);
        }
        if (data == null) {
            List<AxisAlignedBB> blocks = null;
            boolean insideBorder = false;
            d1 = axisScan ? scanY(d1, insideBorder = this.world.a(this.world.getWorldBorder(), this), true) : getMotY(d1, blocks = this.world.getCubes(this, spaceTraveled), false);
            a(this.boundingBox.c(0.0D, d1, 0.0D));
            if (this.world.tacoSpigotConfig.fixEastWest && Math.abs(d0) > Math.abs(d2)) {
                d2 = axisScan ? scanZ(d2, insideBorder, true) : getMotZ(d2, blocks, false);
                a(this.boundingBox.c(0.0D, 0.0D, d2));
                d0 = axisScan ? scanX(d0, insideBorder, true) : getMotX(d0, blocks, false);
                a(this.boundingBox.c(d0, 0.0D, 0.0D));
            } else {
                d0 = axisScan ? scanX(d0, insideBorder, true) : getMotX(d0, blocks, false);
                a(this.boundingBox.c(d0, 0.0D, 0.0D));
                d2 = axisScan ? scanZ(d2, insideBorder, true) : getMotZ(d2, blocks, false);
                a(this.boundingBox.c(0.0D, 0.0D, d2));
            }
            recalcPosition();
            if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.DESTROY_TNT_AND_FALLING_BLOCKS_OUTSIDE_BORDER && !this.world.a(this.world.getWorldBorder(), this)) {
                die();
                if (axisScan)
                    this.world.movementCache.put(entity, new World.MovementData(null, null));
                return;
            }
            this.positionChanged = !(d6 == d0 && d8 == d2);
            this.E = (d7 != d1);
            this.onGround = (this.E && d7 < 0.0D);
            this.F = !(!this.positionChanged && !this.E);
            BlockPosition blockposition = new BlockPosition(this.locX, this.locY - 0.20000000298023224D, this.locZ);
            Block block = this.world.getType(blockposition).getBlock();
            if (block.getMaterial() == Material.AIR) {
                Block block1 = this.world.getType(blockposition.down()).getBlock();
                if (block1 instanceof BlockFence || block1 instanceof BlockCobbleWall || block1 instanceof BlockFenceGate) {
                    block = block1;
                    blockposition = blockposition.down();
                }
            }
            a(d1, this.onGround, block, blockposition);
            if (d6 != d0)
                this.motX = 0.0D;
            if (d8 != d2)
                this.motZ = 0.0D;
            if (d7 != d1)
                block.a(this.world, this);
            if (axisScan && (d7 == d1 || d7 >= 0.0D)) {
                this.world.movementCache.put(entity,
                        new World.MovementData(
                                new double[] {
                                        this.boundingBox.a, this.boundingBox.b, this.boundingBox.c, this.boundingBox.d, this.boundingBox.e, this.boundingBox.f, this.locX, this.locY, this.locZ, this.motX,
                                        this.motY, this.motZ, this.fallDistance }, new boolean[] { this.positionChanged, this.E, this.onGround, this.F }));
            } else if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.TNT_MERGE && this.fuseTicks % 2 == 0) {
                World.ObjectLocation tnt = new World.ObjectLocation(this.locX, this.locY, this.locZ);
                List<EntityTNTPrimed> list = this.world.tntMovementCache.get(tnt);
                if (list == null) {
                    list = new ArrayList<>();
                    this.world.tntMovementCache.put(tnt, list);
                } else {
                    for (EntityTNTPrimed primed : list) {
                        if (this.id - primed.id == primed.amount && primed.fuseTicks == this.fuseTicks - 1 && primed.motX == this.motX && primed.motY == this.motY && primed.motZ == this.motZ) {
                            primed.amount += this.amount;
                            die();
                            return;
                        }
                    }
                }
                list.add(this);
            }
        } else {
            double[] doubles = data.getDoubles();
            if (doubles == null) {
                die();
                return;
            }
            this.boundingBox.a = doubles[0];
            this.boundingBox.b = doubles[1];
            this.boundingBox.c = doubles[2];
            this.boundingBox.d = doubles[3];
            this.boundingBox.e = doubles[4];
            this.boundingBox.f = doubles[5];
            this.locX = doubles[6];
            this.locY = doubles[7];
            this.locZ = doubles[8];
            this.motX = doubles[9];
            this.motY = doubles[10];
            this.motZ = doubles[11];
            this.fallDistance = (float)doubles[12];
            boolean[] booleans = data.getBooleans();
            this.positionChanged = booleans[0];
            this.E = booleans[1];
            this.onGround = booleans[2];
            this.F = booleans[3];
        }
    }
    //TODO ADDED CODE

    protected void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("Fuse", (byte) this.fuseTicks);
        // PaperSpigot start - TNT source location API
        if (sourceLoc != null) {
            nbttagcompound.setInt("SourceLoc_x", sourceLoc.getBlockX());
            nbttagcompound.setInt("SourceLoc_y", sourceLoc.getBlockY());
            nbttagcompound.setInt("SourceLoc_z", sourceLoc.getBlockZ());
        }
        // PaperSpigot end
    }

    protected void a(NBTTagCompound nbttagcompound) {
        this.fuseTicks = nbttagcompound.getByte("Fuse");
        // PaperSpigot start - TNT source location API
        if (nbttagcompound.hasKey("SourceLoc_x")) {
            int srcX = nbttagcompound.getInt("SourceLoc_x");
            int srcY = nbttagcompound.getInt("SourceLoc_y");
            int srcZ = nbttagcompound.getInt("SourceLoc_z");
            sourceLoc = new org.bukkit.Location(world.getWorld(), srcX, srcY, srcZ);
        }
        // PaperSpigot end
    }

    public EntityLiving getSource() {
        return this.source;
    }

    // PaperSpigot start - Fix cannons
    @Override
    public double f(double d0, double d1, double d2) {
        if (!world.paperSpigotConfig.fixCannons) return super.f(d0, d1, d2);

        double d3 = this.locX - d0;
        double d4 = this.locY + this.getHeadHeight() - d1;
        double d5 = this.locZ - d2;

        return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    }

    @Override
    public boolean aL() {
        return !world.paperSpigotConfig.fixCannons && super.aL();
    }

    @Override
    public float getHeadHeight() {
        return world.paperSpigotConfig.fixCannons ? this.length / 2 : 0.0F;
    }

    /**
     * Author: Jedediah Smith <jedediah@silencegreys.com>
     */
    @Override
    public boolean W() {
        if (!world.paperSpigotConfig.fixCannons) return super.W();

        // Preserve velocity while calling the super method
        double oldMotX = this.motX;
        double oldMotY = this.motY;
        double oldMotZ = this.motZ;

        super.W();

        this.motX = oldMotX;
        this.motY = oldMotY;
        this.motZ = oldMotZ;

        if (this.inWater) {
            // Send position and velocity updates to nearby players on every tick while the TNT is in water.
            // This does pretty well at keeping their clients in sync with the server.
            EntityTrackerEntry ete = ((WorldServer) this.getWorld()).getTracker().trackedEntities.get(this.getId());
            if (ete != null) {
                PacketPlayOutEntityVelocity velocityPacket = new PacketPlayOutEntityVelocity(this);
                PacketPlayOutEntityTeleport positionPacket = new PacketPlayOutEntityTeleport(this);

                for (EntityPlayer viewer : ete.trackedPlayers) {
                    if ((viewer.locX - this.locX) * (viewer.locY - this.locY) * (viewer.locZ - this.locZ) < 16 * 16) {
                        viewer.playerConnection.sendPacket(velocityPacket);
                        viewer.playerConnection.sendPacket(positionPacket);
                    }
                }
            }
        }

        return this.inWater;
    }
    // PaperSpigot end
}
