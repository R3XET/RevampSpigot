package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.revamp.spigot.config.Settings;
import eu.revamp.spigot.optimization.TNTOptimization;
import eu.revamp.spigot.optimization.cache.CoordinateInt;
import eu.revamp.spigot.utils.generic.random.FastRandom;
import net.jafama.FastMath;
import org.bukkit.Location;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.*;

// CraftBukkit start
// CraftBukkit end

public class Explosion {

    private final boolean a;
    private final boolean b;
    //TODO OLD CODE
    //private final Random c = new Random();
    //TODO OLD CODE

    //TODO ADDED CODE
    private final Random c = new FastRandom();
    //TODO ADDED CODE
    private final World world;
    private final double posX;
    private final double posY;
    private final double posZ;
    public final Entity source;
    private final float size;
    private final List<BlockPosition> blocks = Lists.newArrayList();
    private final Map<EntityHuman, Vec3D> k = Maps.newHashMap();
    public boolean wasCanceled = false; // CraftBukkit - add field

    //TODO ADDED CODE
    private  final int multiplier;
    private ArrayList<Block> blockCache = new ArrayList<>();
    public boolean inWater;
    //TODO ADDED CODE


    //TODO OLD CODE
  public Explosion(World world, Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        this.world = world;
        this.source = entity;
        //TODO OLD CODE
        //this.size = (float) Math.max(f, 0.0); // CraftBukkit - clamp bad values
        //TODO OL CODE

        //TODO ADDED CODE
        this.size = (float) FastMath.max(f, 0.0D);
        //TODO ADDED CODE
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
        this.a = flag;
        this.b = flag1;
        //TODO ADDED CODE
        this.multiplier = 1;
        this.inWater = false;
        //TODO ADDED CODE
    }
    //TODO OLD CODE

    //TODO ADDED CODE
    public Explosion(World world, Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1, int multiplier) {
        this.world = world;
        this.source = entity;
        //TODO OLD CODE
        //this.size = (float) Math.max(f, 0.0); // CraftBukkit - clamp bad values
        //TODO OL CODE

        //TODO ADDED CODE
        this.size = (float) FastMath.max(f, 0.0D);
        //TODO ADDED CODE
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
        this.a = flag;
        this.b = flag1;
        this.multiplier = multiplier;
    }


    //TODO ADDED CODE


    public boolean isNotBedrockOnFloor(float dura, int y) {
        return dura != 7006.0f || y != -1;
    }

    public void a() {
        if (this.size < 0.1f) {
            return;
        }
        if (Settings.IMP.SETTINGS.PERFORMANCE.OPTIMIZE_REGION) {
            EntityExplodeEvent event = new EntityExplodeEvent(this.source.getBukkitEntity(), new Location(this.world.getWorld(), this.posX, this.posY, this.posZ), new ArrayList<>(), 0.3f);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                this.wasCanceled = true;
            }
        }
        org.bukkit.block.Block b = new Location(this.world.getWorld(), this.posX, this.posY, this.posZ).getBlock();
        if (!this.wasCanceled && (!this.world.tacoSpigotConfig.optimizeLiquidExplosions || !b.isLiquid())) {
            BlockPosition bp = new BlockPosition(this.posX, this.posY, this.posZ);
            ArrayList<BlockPosition> toBreak = new ArrayList<>();
            ArrayList<CoordinateInt> noDura = new ArrayList<>();
            ArrayList<CoordinateInt> noDura2 = new ArrayList<>();
            ArrayList<Boolean> processedCache = (ArrayList<Boolean>) TNTOptimization.example.clone();
            for (int x = -1; x <= 1; ++x) {
                for (int z = -1; z <= 1; ++z) {
                    for (int y = -1; y <= 1; ++y) {
                        int cacheNum = z + 6 + 11 * (y + 6) - 11 + 121 * (x + 6) - 121 - 1;
                        if (!processedCache.get(cacheNum)) {
                            processedCache.set(cacheNum, true);
                            BlockPosition c = bp.a(x, y, z);
                            Block bl = this.world.getType(c).getBlock();
                            float dura = bl.durability;
                            if (dura < 50.0f && dura > 0.0f) {
                                toBreak.add(c);
                                this.blockCache.add(bl);
                                if (dura <= 3.0f) {
                                    noDura.add(new CoordinateInt(x, y, z));
                                }
                                else {
                                    if (this.multiplier >= 2) {
                                        noDura.add(new CoordinateInt(x, y, z));
                                    }
                                }
                            }
                            else if (dura == 0.0f) {
                                noDura.add(new CoordinateInt(x, y, z));
                            }
                            //TODO FINIRE
                            /*
                            else if (RevampSpigotConfig.obsidianDestoyer && dura > 600.0f && dura < 8000.0f && (Settings.IMP.SETTINGS.MISCELLANOUS.CREEPER_DAMAGE_OBSIDIAN || this.source.isTNT) && this.isNotBedrockOnFloor(dura, y)) {
                                Integer current = TNTOptimization.obbyDuras.get(c);
                                if (current != null) {
                                    int newValue = current - this.multiplier;
                                    if (newValue <= 0) {
                                        TNTOptimization.obbyDuras.remove(c);
                                        toBreak.add(c);
                                        this.blockCache.add(bl);
                                        if (this.multiplier >= 2) {
                                            noDura.add(new CoordinateInt(x, y, z));
                                        }
                                    }
                                    else {
                                        TNTOptimization.obbyDuras.put(c, newValue);
                                    }
                                }
                                else {
                                    int originalDura = this.getOriginalDura(dura);
                                    if (originalDura != -1) {
                                        TNTOptimization.obbyDuras.put(c, originalDura - this.multiplier);
                                    }
                                }
                            }
                            */
                            //TODO FINIRE
                        }
                    }
                }
            }
            for (CoordinateInt a : noDura) {
                int xRef = a.x;
                int yRef = a.y;
                int zRef = a.z;
                BlockPosition bpRef = bp.a(xRef, yRef, zRef);
                int y2 = -1;
                int y3 = 1;
                if (yRef > 0) {
                    y2 = 0;
                }
                else if (yRef < 0) {
                    y3 = 0;
                }
                int x2 = -1;
                int x3 = 1;
                if (xRef > 0) {
                    x2 = 0;
                }
                else if (xRef < 0) {
                    x3 = 0;
                }
                int z2 = -1;
                int z3 = 0x38B34799 ^ 0x38B34798;
                if (zRef > 0) {
                    z2 = 0;
                }
                else if (zRef < 0) {
                    z3 = 0;
                }
                for (int x4 = x2; x4 <= x3; ++x4) {
                    for (int y4 = y2; y4 <= y3; ++y4) {
                        for (int z4 = z2; z4 <= z3; ++z4) {
                            int totalXRef = x4 + xRef;
                            int totalYRef = y4 + yRef;
                            int totalZRef = z4 + zRef;
                            int cacheNum2 = totalZRef + 6 + 11 * (totalYRef + 6) - 11 + 121 * (totalXRef + 6) - 121 - 1;
                            if (!processedCache.get(cacheNum2)) {
                                processedCache.set(cacheNum2, true);
                                BlockPosition c2 = bpRef.a(x4, y4, z4);
                                Block bl2 = this.world.getType(c2).getBlock();
                                float dura2 = bl2.durability;
                                if (dura2 < 50.0f && dura2 > 0.0f) {
                                    toBreak.add(c2);
                                    if (dura2 <= 3.0f) {
                                        this.blockCache.add(bl2);
                                        noDura2.add(new CoordinateInt(totalXRef, totalYRef, totalZRef));
                                    }
                                    else {
                                        this.blockCache.add(bl2);
                                        if (this.multiplier >= 3) {
                                            noDura2.add(new CoordinateInt(totalXRef, totalYRef, totalZRef));
                                        }
                                    }
                                }
                                else if (dura2 == 0.0f) {
                                    noDura2.add(new CoordinateInt(totalXRef, totalYRef, totalZRef));
                                }
                                //TODO FINIRE
                                /*
                                else if (RevampSpigotConfig.obsidianDestoyer && dura2 > 600.0f && dura2 < 8000.0f && (Settings.IMP.SETTINGS.MISCELLANOUS.CREEPER_DAMAGE_OBSIDIAN || this.source.isTNT) && this.isNotBedrockOnFloor(dura2, y4)) {
                                    Integer current2 = TNTOptimization.obbyDuras.get(c2);
                                    if (current2 != null) {
                                        int newValue2 = current2 - this.multiplier;
                                        if (newValue2 <= 0) {
                                            TNTOptimization.obbyDuras.remove(c2);
                                            toBreak.add(c2);
                                            this.blockCache.add(bl2);
                                            if (this.multiplier >= 3) {
                                                noDura2.add(new CoordinateInt(totalXRef, totalYRef, totalZRef));
                                            }
                                        }
                                        else {
                                            TNTOptimization.obbyDuras.put(c2, newValue2);
                                        }
                                    }
                                    else {
                                        int originalDura2 = this.getOriginalDura(dura2);
                                        if (originalDura2 != -1) {
                                            TNTOptimization.obbyDuras.put(c2, originalDura2 - this.multiplier);
                                        }
                                    }
                                }*/
                                //TODO FINIRE
                            }
                        }
                    }
                }
            }
            noDura.clear();
            for (CoordinateInt a : noDura2) {
                int xRef = a.x;
                int yRef = a.y;
                int zRef = a.z;
                BlockPosition bpRef = bp.a(xRef, yRef, zRef);
                int y2 = -1;
                int y3 = 1;
                if (yRef > 0) {
                    y2 = (0);
                }
                else if (yRef < 0) {
                    y3 = (0);
                }
                int x2 = -1;
                int x3 = 1;
                if (xRef > 0) {
                    x2 = (0);
                }
                else if (xRef < 0) {
                    x3 = (0);
                }
                int z2 = -1;
                int z3 = 1;
                if (zRef > 0) {
                    z2 = (0);
                }
                else if (zRef < 0) {
                    z3 = (0);
                }
                for (int x4 = x2; x4 <= x3; ++x4) {
                    for (int y4 = y2; y4 <= y3; ++y4) {
                        for (int z4 = z2; z4 <= z3; ++z4) {
                            int totalXRef = x4 + xRef;
                            int totalYRef = y4 + yRef;
                            int totalZRef = z4 + zRef;
                            int cacheNum2 = totalZRef + 6 + 11 * (totalYRef + 6) - 11 + 121 * (totalXRef + 6) - 121 - 1;
                            if (!processedCache.get(cacheNum2)) {
                                processedCache.set(cacheNum2, true);
                                int result = 0;
                                if (totalXRef == 3 || totalXRef == -3) {
                                    ++result;
                                }
                                if (totalYRef == 3 || totalYRef == -3) {
                                    ++result;
                                }
                                if (totalZRef == 3 || totalZRef == -3) {
                                    ++result;
                                }
                                if (result < 2) {
                                    BlockPosition c3 = bpRef.a(x4, y4, z4);
                                    Block bl3 = this.world.getType(c3).getBlock();
                                    float dura3 = bl3.durability;
                                    if (dura3 < 50.0f && dura3 > 0.0f) {
                                        toBreak.add(c3);
                                        this.blockCache.add(bl3);
                                        if (dura3 <= 3.0f && this.multiplier >= 2) {
                                            noDura.add(new CoordinateInt(totalXRef, totalYRef, totalZRef));
                                        }
                                    }
                                    else if (dura3 == 0.0f) {
                                        noDura.add(new CoordinateInt(totalXRef, totalYRef, totalZRef));
                                    }
                                    //TODO FINIRE
                                    /*
                                    else if (RevampSpigotConfig.obsidianDestoyer && dura3 > 600.0f && dura3 < 8000.0f && (Settings.IMP.SETTINGS.MISCELLANOUS.CREEPER_DAMAGE_OBSIDIAN || this.source.isTNT) && this.isNotBedrockOnFloor(dura3, y4)) {
                                        Integer current3 = TNTOptimization.obbyDuras.get(c3);
                                        if (current3 != null) {
                                            int newValue3 = current3 - this.multiplier;
                                            if (newValue3 <= 0) {
                                                TNTOptimization.obbyDuras.remove(c3);
                                                toBreak.add(c3);
                                                this.blockCache.add(bl3);
                                            }
                                            else {
                                                TNTOptimization.obbyDuras.put(c3, newValue3);
                                            }
                                        }
                                        else {
                                            int originalDura3 = this.getOriginalDura(dura3);
                                            if (originalDura3 != -1) {
                                                TNTOptimization.obbyDuras.put(c3, originalDura3 - this.multiplier);
                                            }
                                        }
                                    }
                                    */
                                    //TODO FINIRE
                                }
                                else {
                                    processedCache.set(cacheNum2, false);
                                    noDura.add(a);
                                }
                            }
                        }
                    }
                }
            }
            noDura2.clear();
            ArrayList<Boolean> airBrokeABlock = (ArrayList<Boolean>)TNTOptimization.example.clone();
            for (CoordinateInt a2 : noDura) {
                int xRef2 = a2.x;
                int yRef2 = a2.y;
                int zRef2 = a2.z;
                BlockPosition bpRef2 = bp.a(xRef2, yRef2, zRef2);
                int y5 = -1;
                int y6 = 1;
                if (yRef2 > 1) {
                    y5 = (0);
                }
                else if (yRef2 < -1) {
                    y6 = (0);
                }
                int x5 = -1;
                int x6 = 1;
                if (xRef2 > 1) {
                    x5 = (0);
                }
                else if (xRef2 < -1) {
                    x6 = (0);
                }
                int z5 = -1;
                int z6 = 1;
                if (zRef2 > 1) {
                    z5 = (0);
                }
                else if (zRef2 < -1) {
                    z6 = (0);
                }
                boolean brokeABlock = false;
                ArrayList<CoordinateInt> tempAirs = new ArrayList<CoordinateInt>();
                for (int x7 = x5; x7 <= x6; ++x7) {
                    for (int y7 = y5; y7 <= y6; ++y7) {
                        for (int z7 = z5; z7 <= z6; ++z7) {
                            int totalXRef2 = x7 + xRef2;
                            int totalYRef2 = y7 + yRef2;
                            int totalZRef2 = z7 + zRef2;
                            int cacheNum3 = totalZRef2 + 6 + 11 * (totalYRef2 + 6) - 11 + 121 * (totalXRef2 + 6) - 121 - 1;
                            if (!processedCache.get(cacheNum3)) {
                                processedCache.set(cacheNum3, true);
                                BlockPosition c4 = bpRef2.a(x7, y7, z7);
                                Block bl4 = this.world.getType(c4).getBlock();
                                float dura4 = bl4.durability;
                                if (dura4 <= 3.0f && dura4 > 0.0f) {
                                    brokeABlock = true;
                                    toBreak.add(c4);
                                    this.blockCache.add(bl4);
                                    if (this.multiplier >= 3) {
                                        noDura2.add(new CoordinateInt(totalXRef2, totalYRef2, totalZRef2));
                                    }
                                }
                                else if (dura4 == 0.0f) {
                                    CoordinateInt value = new CoordinateInt(totalXRef2, totalYRef2, totalZRef2);
                                    tempAirs.add(value);
                                    noDura2.add(value);
                                }
                                //TODO FINIRE
                                /*
                                else if (RevampSpigotConfig.obsididanDestroyerLargeRadius && RevampSpigotConfig.obsidianDestoyer && dura4 > 600.0f && dura4 < 8000.0f && (Settings.IMP.SETTINGS.MISCELLANOUS.CREEPER_DAMAGE_OBSIDIAN || this.source.isTNT) && this.isNotBedrockOnFloor(dura4, y7)) {
                                    Integer current4 = TNTOptimization.obbyDuras.get(c4);
                                    if (current4 != null) {
                                        int newValue4 = current4 - this.multiplier;
                                        if (newValue4 <= 0) {
                                            TNTOptimization.obbyDuras.remove(c4);
                                            toBreak.add(c4);
                                            this.blockCache.add(bl4);
                                        }
                                        else {
                                            TNTOptimization.obbyDuras.put(c4, newValue4);
                                        }
                                    }
                                    else {
                                        int originalDura4 = this.getOriginalDura(dura4);
                                        if (originalDura4 != -1) {
                                            TNTOptimization.obbyDuras.put(c4, originalDura4 - this.multiplier);
                                        }
                                    }
                                }*/
                                //TODO FINIRE
                            }
                        }
                    }
                }
                if (brokeABlock) {
                    for (CoordinateInt a3 : tempAirs) {
                        airBrokeABlock.set(a3.z + 6 + 11 * (a3.y + 6) - 11 + 121 * (a3.x + 6) - 121 - 1, true);
                    }
                }
                tempAirs.clear();
            }
            noDura.clear();
            for (CoordinateInt a2 : noDura2) {
                int xRef2 = a2.x;
                int yRef2 = a2.y;
                int zRef2 = a2.z;
                if (airBrokeABlock.get(zRef2 + 6 + 11 * (yRef2 + 6) - 11 + 121 * (xRef2 + 6) - 121 - 1)) {
                    continue;
                }
                BlockPosition bpRef2 = bp.a(xRef2, yRef2, zRef2);
                int y5 = -1;
                int y6 = 1;
                if (yRef2 > 1) {
                    y5 = (0);
                }
                else if (yRef2 < -1) {
                    y6 = (0);
                }
                int x5 = -1;
                int x6 = 1;
                if (xRef2 > 1) {
                    x5 = (0);
                }
                else if (xRef2 < -1) {
                    x6 = (0);
                }
                int z5 = -1;
                int z6 = 1;
                if (zRef2 > 1) {
                    z5 = (0);
                }
                else if (zRef2 < -1) {
                    z6 = (0);
                }
                for (int x8 = x5; x8 <= x6; ++x8) {
                    for (int y8 = y5; y8 <= y6; ++y8) {
                        for (int z8 = z5; z8 <= z6; ++z8) {
                            int totalXRef3 = x8 + xRef2;
                            int totalYRef3 = y8 + yRef2;
                            int totalZRef3 = z8 + zRef2;
                            int cacheNum4 = totalZRef3 + 6 + 11 * (totalYRef3 + 6) - 11 + 121 * (totalXRef3 + 6) - 121 - 1;
                            if (!processedCache.get(cacheNum4)) {
                                processedCache.set(cacheNum4, true);
                                BlockPosition c3 = bpRef2.a(x8, y8, z8);
                                Block bl3 = this.world.getType(c3).getBlock();
                                float dura3 = bl3.durability;
                                if (dura3 <= 3.0f && dura3 > 0.0f) {
                                    toBreak.add(c3);
                                    this.blockCache.add(bl3);
                                }
                                //TODO FINIRE
                                /*
                                else if (RevampSpigotConfig.obsididanDestroyerLargeRadius && RevampSpigotConfig.obsidianDestoyer && dura3 > 600.0f && dura3 < 8000.0f && (Settings.IMP.SETTINGS.MISCELLANOUS.CREEPER_DAMAGE_OBSIDIAN || this.source.isTNT) && this.isNotBedrockOnFloor(dura3, y8)) {
                                    Integer current3 = TNTOptimization.obbyDuras.get(c3);
                                    if (current3 != null) {
                                        int newValue3 = current3 - this.multiplier;
                                        if (newValue3 <= 0) {
                                            TNTOptimization.obbyDuras.remove(c3);
                                            toBreak.add(c3);
                                            this.blockCache.add(bl3);
                                        }
                                        else {
                                            TNTOptimization.obbyDuras.put(c3, newValue3);
                                        }
                                    }
                                    else {
                                        int originalDura3 = this.getOriginalDura(dura3);
                                        if (originalDura3 != -1) {
                                            TNTOptimization.obbyDuras.put(c3, originalDura3 - this.multiplier);
                                        }
                                    }
                                }*/
                                //TODO FINIRE
                            }
                        }
                    }
                }
            }
            this.blocks.addAll(toBreak);
        }
        float f3 = this.size * 2.0f;
        List<Entity> list = this.world.aTNT(this.source, new AxisAlignedBB(MathHelper.floor(this.posX - f3 - 1.0), MathHelper.floor(this.posY - f3 - 1.0), MathHelper.floor(this.posZ - f3 - 1.0), MathHelper.floor(this.posX + f3 + 1.0), MathHelper.floor(this.posY + f3 + 1.0), MathHelper.floor(this.posZ + f3 + 1.0)));
        if (!this.source.skipVelocityMultiplier) {
            for (int l1 = 0; l1 < list.size(); ++l1) {
                Entity entity = list.get(l1);
                if (entity.processedInExplosion) {
                    entity.processedInExplosion = false;
                }
                else if (entity.locX != this.posX || entity.locY != this.posY || entity.locZ != this.posZ) {
                    double d8 = entity.locX - this.posX;
                    double d9 = entity.locY + entity.getHeadHeight() - this.posY;
                    double d10 = entity.locZ - this.posZ;
                    double d8_squared = d8 * d8;
                    double d9_squared = d9 * d9;
                    double d10_squared = d10 * d10;
                    double d11 = Math.sqrt(d8_squared + d9_squared + d10_squared) / f3;
                    if (d11 <= 1.0) {
                        double d12 = (float)Math.sqrt(d8_squared + d9 * d9 + d10_squared);
                        if (d12 != 0.0) {
                            d8 /= d12;
                            d9 /= d12;
                            d10 /= d12;
                            double d13 = (1.0 - d11) * this.getBlockDensity(new Vec3D(this.posX, this.posY, this.posZ), entity.getBoundingBox());
                            double xVel = d8 * d13 * this.multiplier;
                            double yVel = d9 * d13 * this.multiplier;
                            double zVel = d10 * d13 * this.multiplier;
                            if (l1 != list.size()) {
                                for (Entity e2 : list.subList(l1 + 1, list.size())) {
                                    if (e2.locX == entity.locX && e2.locY == entity.locY && e2.locZ == entity.locZ) {
                                        e2.g(xVel, yVel, zVel);
                                        e2.processedInExplosion = true;
                                    }
                                }
                            }
                            entity.g(xVel, yVel, zVel);
                        }
                    }
                }
            }
        }
        else {
            for (int l1 = 0; l1 < list.size(); ++l1) {
                Entity entity = list.get(l1);
                if (entity.processedInExplosion) {
                    entity.processedInExplosion = false;
                }
                else if (entity.locX != this.posX || entity.locY != this.posY || entity.locZ != this.posZ) {
                    double d8 = entity.locX - this.posX;
                    double d9 = entity.locY + entity.getHeadHeight() - this.posY;
                    double d10 = entity.locZ - this.posZ;
                    double d8_squared = d8 * d8;
                    double d9_squared = d9 * d9;
                    double d10_squared = d10 * d10;
                    double d11 = Math.sqrt(d8_squared + d9_squared + d10_squared) / f3;
                    if (d11 <= 1.0) {
                        double d12 = (float)Math.sqrt(d8_squared + d9 * d9 + d10_squared);
                        if (d12 != 0.0) {
                            d8 /= d12;
                            d9 /= d12;
                            d10 /= d12;
                            double d13 = (1.0 - d11) * this.getBlockDensity(new Vec3D(this.posX, this.posY, this.posZ), entity.getBoundingBox());
                            double xVel = d8 * d13;
                            double yVel = d9 * d13;
                            double zVel = d10 * d13;
                            if (l1 != list.size()) {
                                for (Entity e2 : list.subList(l1 + 1, list.size())) {
                                    if (e2.locX == entity.locX && e2.locY == entity.locY && e2.locZ == entity.locZ) {
                                        e2.g(xVel, yVel, zVel);
                                        e2.processedInExplosion = true;
                                    }
                                }
                            }
                            entity.g(xVel, yVel, zVel);
                        }
                    }
                }
            }
        }
    }





    /*
    public void a() {
        // CraftBukkit start
        if (this.size < 0.1F) {
            return;
        }
        // CraftBukkit end
        HashSet hashset = Sets.newHashSet();
        boolean flag = true;

        int i;
        int j;

        Block b = world.getChunkAt((int)posX >> 4, (int)posZ >> 4).getBlockData(new BlockPosition(posX, posY, posZ)).getBlock(); // TacoSpigot - get block of the explosion

        if (!this.world.tacoSpigotConfig.optimizeLiquidExplosions || !b.getMaterial().isLiquid()) { //TacoSpigot - skip calculating what blocks to blow up in water/lava
        for (int k = 0; k < 16; ++k) {
            for (i = 0; i < 16; ++i) {
                for (j = 0; j < 16; ++j) {
                    if (k == 0 || k == 15 || i == 0 || i == 15 || j == 0 || j == 15) {
                        double d0 = (float) k / 15.0F * 2.0F - 1.0F;
                        double d1 = (float) i / 15.0F * 2.0F - 1.0F;
                        double d2 = (float) j / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);

                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
                        double d4 = this.posX;
                        double d5 = this.posY;
                        double d6 = this.posZ;

                        for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                            BlockPosition blockposition = new BlockPosition(d4, d5, d6);
                            IBlockData iblockdata = this.world.getType(blockposition);

                            if (iblockdata.getBlock().getMaterial() != Material.AIR) {
                                float f2 = this.source != null ? this.source.a(this, this.world, blockposition, iblockdata) : iblockdata.getBlock().a((Entity) null);

                                f -= (f2 + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && (this.source == null || this.source.a(this, this.world, blockposition, iblockdata, f)) && blockposition.getY() < 256 && blockposition.getY() >= 0) { // CraftBukkit - don't wrap explosions
                                hashset.add(blockposition);
                            }

                            d4 += d0 * 0.30000001192092896D;
                            d5 += d1 * 0.30000001192092896D;
                            d6 += d2 * 0.30000001192092896D;
                        }
                    }
                }
            }
        }
        }

        this.blocks.addAll(hashset);

//TODO OLD CODE
        /*
        float f3 = this.size * 2.0F;

        i = MathHelper.floor(this.posX - (double) f3 - 1.0D);
        j = MathHelper.floor(this.posX + (double) f3 + 1.0D);
        int l = MathHelper.floor(this.posY - (double) f3 - 1.0D);
        int i1 = MathHelper.floor(this.posY + (double) f3 + 1.0D);
        int j1 = MathHelper.floor(this.posZ - (double) f3 - 1.0D);
        int k1 = MathHelper.floor(this.posZ + (double) f3 + 1.0D);
        // PaperSpigot start - Fix lag from explosions processing dead entities
        List list = this.world.a(this.source, new AxisAlignedBB(i, l, j1, j, i1, k1), new com.google.common.base.Predicate<Entity>() {
            @Override
            public boolean apply(Entity entity) {
                return IEntitySelector.d.apply(entity) && !entity.dead;
            }
        });
        // PaperSpigot end
        Vec3D vec3d = new Vec3D(this.posX, this.posY, this.posZ);

        for (Object o : list) {
            Entity entity = (Entity) o;

            if (!entity.aW()) {
                double d7 = entity.f(this.posX, this.posY, this.posZ) / (double) f3;

                if (d7 <= 1.0D) {
                    double d8 = entity.locX - this.posX;
                    double d9 = entity.locY + (double) entity.getHeadHeight() - this.posY;
                    double d10 = entity.locZ - this.posZ;
                    double d11 = MathHelper.sqrt(d8 * d8 + d9 * d9 + d10 * d10);

                    if (d11 != 0.0D) {
                        d8 /= d11;
                        d9 /= d11;
                        d10 /= d11;
                        double d12 = this.getBlockDensity(vec3d, entity.getBoundingBox()); // PaperSpigot - Optimize explosions
                        double d13 = (1.0D - d7) * d12;

                        // entity.damageEntity(DamageSource.explosion(this), (float) ((int) ((d13 * d13 + d13) / 2.0D * 8.0D * (double) f3 + 1.0D)));+                        // CraftBukkit start
                        CraftEventFactory.entityDamage = source;
                        entity.forceExplosionKnockback = false;
                        boolean wasDamaged = entity.damageEntity(DamageSource.explosion(this), (float) ((int) ((d13 * d13 + d13) / 2.0D * 8.0D * (double) f3 + 1.0D)));
                        CraftEventFactory.entityDamage = null;
                        if (!wasDamaged && !(entity instanceof EntityTNTPrimed || entity instanceof EntityFallingBlock) && !entity.forceExplosionKnockback) {
                            continue;
                        }
                        // CraftBukkit end
                        double d14 = entity instanceof EntityHuman && world.paperSpigotConfig.disableExplosionKnockback ? 0 : EnchantmentProtection.a(entity, d13); // PaperSpigot

                        // PaperSpigot start - Fix cannons
                        /*
                        entity.motX += d8 * d14;
                        entity.motY += d9 * d14;
                        entity.motZ += d10 * d14;
                        */
                        /*
                        // This impulse method sets the dirty flag, so clients will get an immediate velocity update
                        entity.g(d8 * d14, d9 * d14, d10 * d14);
                        // PaperSpigot end

                        if (entity instanceof EntityHuman && !((EntityHuman) entity).abilities.isInvulnerable && !world.paperSpigotConfig.disableExplosionKnockback) { // PaperSpigot
                            this.k.put((EntityHuman) entity, new Vec3D(d8 * d13, d9 * d13, d10 * d13));
                        }
                    }
                }
            }
        }
        */
        //TODO OLD CODE
/*

        //TODO ADDED CODE

        if (!this.source.skipVelocityMultiplier) {
            float f3 = this.size * 2.0f;
            List<Entity> list = this.world.aTNT(this.source, new AxisAlignedBB(MathHelper.floor(this.posX - f3 - 1.0), MathHelper.floor(this.posY - f3 - 1.0), MathHelper.floor(this.posZ - f3 - 1.0), MathHelper.floor(this.posX + f3 + 1.0), MathHelper.floor(this.posY + f3 + 1.0), MathHelper.floor(this.posZ + f3 + 1.0)));
            for (int l1 = 0; l1 < list.size(); ++l1) {
                Entity entity = list.get(l1);
                if (entity.processedInExplosion) {
                    entity.processedInExplosion = false;
                }
                else if (entity.locX != this.posX || entity.locY != this.posY || entity.locZ != this.posZ) {
                    double d8 = entity.locX - this.posX;
                    double d9 = entity.locY + entity.getHeadHeight() - this.posY;
                    double d10 = entity.locZ - this.posZ;
                    double d8_squared = d8 * d8;
                    double d9_squared = d9 * d9;
                    double d10_squared = d10 * d10;
                    double d11 = Math.sqrt(d8_squared + d9_squared + d10_squared) / f3;
                    if (d11 <= 1.0) {
                        double d12 = (float)Math.sqrt(d8_squared + d9 * d9 + d10_squared);
                        if (d12 != 0.0) {
                            d8 /= d12;
                            d9 /= d12;
                            d10 /= d12;
                            double d13 = (1.0 - d11) * this.getBlockDensity(new Vec3D(this.posX, this.posY, this.posZ), entity.getBoundingBox());
                            double xVel = d8 * d13 * this.multiplier;
                            double yVel = d9 * d13 * this.multiplier;
                            double zVel = d10 * d13 * this.multiplier;
                            if (l1 != list.size()) {
                                for (Entity e2 : list.subList(l1 + (0x13DFDD00 ^ 0x13DFDD01), list.size())) {
                                    if (e2.locX == entity.locX && e2.locY == entity.locY && e2.locZ == entity.locZ) {
                                        e2.g(xVel, yVel, zVel);
                                        e2.processedInExplosion = true;
                                    }
                                }
                            }
                            entity.g(xVel, yVel, zVel);
                        }
                    }
                }
            }
        }
        else {
            float f3 = this.size * 2.0f;
            List<Entity> list = this.world.aTNT(this.source, new AxisAlignedBB(MathHelper.floor(this.posX - f3 - 1.0), MathHelper.floor(this.posY - f3 - 1.0), MathHelper.floor(this.posZ - f3 - 1.0), MathHelper.floor(this.posX + f3 + 1.0), MathHelper.floor(this.posY + f3 + 1.0), MathHelper.floor(this.posZ + f3 + 1.0)));
            for (int l1 = 0; l1 < list.size(); ++l1) {
                Entity entity = list.get(l1);
                if (entity.processedInExplosion) {
                    entity.processedInExplosion = false;
                }
                else if (entity.locX != this.posX || entity.locY != this.posY || entity.locZ != this.posZ) {
                    double d8 = entity.locX - this.posX;
                    double d9 = entity.locY + entity.getHeadHeight() - this.posY;
                    double d10 = entity.locZ - this.posZ;
                    double d8_squared = d8 * d8;
                    double d9_squared = d9 * d9;
                    double d10_squared = d10 * d10;
                    double d11 = Math.sqrt(d8_squared + d9_squared + d10_squared) / f3;
                    if (d11 <= 1.0) {
                        double d12 = (float)Math.sqrt(d8_squared + d9 * d9 + d10_squared);
                        if (d12 != 0.0) {
                            d8 /= d12;
                            d9 /= d12;
                            d10 /= d12;
                            double d13 = (1.0 - d11) * this.getBlockDensity(new Vec3D(this.posX, this.posY, this.posZ), entity.getBoundingBox());
                            double xVel = d8 * d13;
                            double yVel = d9 * d13;
                            double zVel = d10 * d13;
                            if (l1 != list.size()) {
                                for (Entity e2 : list.subList(l1 + (0xC8B0B656 ^ 0xC8B0B657), list.size())) {
                                    if (e2.locX == entity.locX && e2.locY == entity.locY && e2.locZ == entity.locZ) {
                                        e2.g(xVel, yVel, zVel);
                                        e2.processedInExplosion = true;
                                    }
                                }
                            }
                            entity.g(xVel, yVel, zVel);
                        }
                    }
                }
            }
        }
        //TODO ADDED CODE

    }
    */

    public void a(boolean flag) {
        // PaperSpigot start - Configurable TNT explosion volume.
        float volume = source instanceof EntityTNTPrimed ? world.paperSpigotConfig.tntExplosionVolume : 4.0F;
        this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", volume, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        // PaperSpigot end
        if (this.size >= 2.0F && this.b) {
            this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        } else {
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;

        //TODO OLD CODE
        //BlockPosition blockposition;
        //TODO OLD CODE

        if (this.b) {
            // CraftBukkit start
            org.bukkit.World bworld = this.world.getWorld();
            org.bukkit.entity.Entity explode = this.source == null ? null : this.source.getBukkitEntity();
            Location location = new Location(bworld, this.posX, this.posY, this.posZ);

            List<org.bukkit.block.Block> blockList = Lists.newArrayList();
            for (int i1 = this.blocks.size() - 1; i1 >= 0; i1--) {
                BlockPosition cpos = this.blocks.get(i1);
                org.bukkit.block.Block bblock = bworld.getBlockAt(cpos.getX(), cpos.getY(), cpos.getZ());
                if (bblock.getType() != org.bukkit.Material.AIR) {
                    blockList.add(bblock);
                }
            }

            boolean cancelled;
            List<org.bukkit.block.Block> bukkitBlocks;
            float yield;

            if (explode != null) {
                EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList, 0.3F);
                this.world.getServer().getPluginManager().callEvent(event);
                cancelled = event.isCancelled();
                bukkitBlocks = event.blockList();
                yield = event.getYield();
            } else {
                BlockExplodeEvent event = new BlockExplodeEvent(location.getBlock(), blockList, 0.3F);
                this.world.getServer().getPluginManager().callEvent(event);
                cancelled = event.isCancelled();
                bukkitBlocks = event.blockList();
                yield = event.getYield();
            }

            this.blocks.clear();

            for (org.bukkit.block.Block bblock : bukkitBlocks) {
                BlockPosition coords = new BlockPosition(bblock.getX(), bblock.getY(), bblock.getZ());
                blocks.add(coords);
            }

            if (cancelled) {
                this.wasCanceled = true;
                return;
            }
            // CraftBukkit end

            //TODO ADDED CODE

            int i = -1;
            for (BlockPosition bp : this.blocks) {
                ++i;
                BlockPosition blockposition = bp;
                Block block = this.blockCache.get(i);

                //TODO ADDED CODE
                if (!Settings.IMP.SETTINGS.PERFORMANCE.DISABLE_ANTI_XRAY)
                    this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
                //TODO ADDED CODE

                if (block.a(this)) {
                    if (block instanceof BlockContainer) {
                        block.dropNaturally(this.world, blockposition, this.world.getType(blockposition), yield, 0);
                    }
                    else {
                        this.world.setAirNoLightUpdate(blockposition);
                    }
                }
                this.world.setTypeAndDataNoLightUpdate(blockposition, Blocks.AIR.getBlockData(), 3);
                block.wasExploded(this.world, blockposition, this);
            }


            //TODO ADDED CODE



            //TODO OLD CODE
            /*
            iterator = this.blocks.iterator();

            while (iterator.hasNext()) {
                blockposition = (BlockPosition) iterator.next();
                Block block = this.world.getType(blockposition).getBlock();

                world.spigotConfig.antiXrayInstance.updateNearbyBlocks(world, blockposition); // Spigot
                if (flag) {
                    double d0 = (float) blockposition.getX() + this.world.random.nextFloat();
                    double d1 = (float) blockposition.getY() + this.world.random.nextFloat();
                    double d2 = (float) blockposition.getZ() + this.world.random.nextFloat();
                    double d3 = d0 - this.posX;
                    double d4 = d1 - this.posY;
                    double d5 = d2 - this.posZ;
                    double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);

                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double) this.size + 0.1D);

                    d7 *= this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F;
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, (d0 + this.posX * 1.0D) / 2.0D, (d1 + this.posY * 1.0D) / 2.0D, (d2 + this.posZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.world.addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
                }

                if (block.getMaterial() != Material.AIR) {
                    if (block.a(this)) {
                        // CraftBukkit - add yield
                        block.dropNaturally(this.world, blockposition, this.world.getType(blockposition), yield, 0);
                    }

                    this.world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
                    block.wasExploded(this.world, blockposition, this);
                }
            }
            */
            //TODO OLD CODE
        }

        if (this.a) {
            iterator = this.blocks.iterator();

            while (iterator.hasNext()) {
                BlockPosition blockposition = (BlockPosition) iterator.next();
                if (this.world.getType(blockposition).getBlock().getMaterial() == Material.AIR && this.world.getType(blockposition.down()).getBlock().o() && this.c.nextInt(3) == 0) {
                    // CraftBukkit start - Ignition by explosion
                    if (!org.bukkit.craftbukkit.event.CraftEventFactory.callBlockIgniteEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this).isCancelled()) {
                        this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
                    }
                    // CraftBukkit end
                }
            }
        }

    }

    public Map<EntityHuman, Vec3D> b() {
        return this.k;
    }

    public EntityLiving getSource() {
        // CraftBukkit start - obtain Fireball shooter for explosion tracking
        return this.source == null ? null : (this.source instanceof EntityTNTPrimed ? ((EntityTNTPrimed) this.source).getSource() : (this.source instanceof EntityLiving ? (EntityLiving) this.source : (this.source instanceof EntityFireball ? ((EntityFireball) this.source).shooter : null)));
        // CraftBukkit end
    }

    public void clearBlocks() {
        this.blocks.clear();
    }

    public List<BlockPosition> getBlocks() {
        return this.blocks;
    }

    // PaperSpigot start - Optimize explosions
    private float getBlockDensity(Vec3D vec3d, AxisAlignedBB aabb) {
        if (!this.world.paperSpigotConfig.optimizeExplosions) {
            return this.world.a(vec3d, aabb);
        }

        CacheKey key = new CacheKey(this, aabb);
        Float blockDensity = this.world.explosionDensityCache.get(key);
        if (blockDensity == null) {
            blockDensity = this.world.a(vec3d, aabb);
            this.world.explosionDensityCache.put(key, blockDensity);
        }

        return blockDensity;
    }

    static class CacheKey {
        private final World world;
        private final double posX, posY, posZ;
        private final double minX, minY, minZ;
        private final double maxX, maxY, maxZ;

        public CacheKey(Explosion explosion, AxisAlignedBB aabb) {
            this.world = explosion.world;
            this.posX = explosion.posX;
            this.posY = explosion.posY;
            this.posZ = explosion.posZ;
            this.minX = aabb.a;
            this.minY = aabb.b;
            this.minZ = aabb.c;
            this.maxX = aabb.d;
            this.maxY = aabb.e;
            this.maxZ = aabb.f;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (Double.compare(cacheKey.posX, posX) != 0) return false;
            if (Double.compare(cacheKey.posY, posY) != 0) return false;
            if (Double.compare(cacheKey.posZ, posZ) != 0) return false;
            if (Double.compare(cacheKey.minX, minX) != 0) return false;
            if (Double.compare(cacheKey.minY, minY) != 0) return false;
            if (Double.compare(cacheKey.minZ, minZ) != 0) return false;
            if (Double.compare(cacheKey.maxX, maxX) != 0) return false;
            if (Double.compare(cacheKey.maxY, maxY) != 0) return false;
            if (Double.compare(cacheKey.maxZ, maxZ) != 0) return false;
            return world.equals(cacheKey.world);
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = world.hashCode();
            temp = Double.doubleToLongBits(posX);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(posY);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(posZ);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(minX);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(minY);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(minZ);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(maxX);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(maxY);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(maxZ);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
    // PaperSpigot end
}
