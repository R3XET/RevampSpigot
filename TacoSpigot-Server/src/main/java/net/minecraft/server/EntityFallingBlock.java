package net.minecraft.server;

import com.google.common.collect.Lists;
import eu.revamp.spigot.config.Settings;
import net.jafama.FastMath;
import org.bukkit.craftbukkit.event.CraftEventFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityFallingBlock extends Entity {

    private IBlockData block;
    public int ticksLived;
    public boolean dropItem = true;
    private boolean e;
    public boolean hurtEntities; // PAIL: private -> public
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0F;
    public NBTTagCompound tileEntityData;
    public org.bukkit.Location sourceLoc; // PaperSpigot

    // PaperSpigot start - Add FallingBlock source location API
    public EntityFallingBlock(World world) {
        this(null, world);
    }

    public EntityFallingBlock(org.bukkit.Location loc, World world) {
        super(world);
        sourceLoc = loc;
        this.loadChunks = world.paperSpigotConfig.loadUnloadedFallingBlocks; // PaperSpigot
    }

    public EntityFallingBlock(World world, double d0, double d1, double d2, IBlockData iblockdata) {
        this(null, world, d0, d1, d2, iblockdata);
    }

    public EntityFallingBlock(org.bukkit.Location loc, World world, double d0, double d1, double d2, IBlockData iblockdata) {
        super(world);
        sourceLoc = loc;
    // PaperSpigot end
        this.block = iblockdata;
        this.k = true;
        this.setSize(0.98F, 0.98F);
        this.setPosition(d0, d1, d2);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
        this.loadChunks = world.paperSpigotConfig.loadUnloadedFallingBlocks; // PaperSpigot
    }


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
            if (axisScan && (d7 == d1 || d7 >= 0.0D))
                this.world.movementCache.put(entity,
                        new World.MovementData(
                                new double[] {
                                        this.boundingBox.a, this.boundingBox.b, this.boundingBox.c, this.boundingBox.d, this.boundingBox.e, this.boundingBox.f, this.locX, this.locY, this.locZ, this.motX,
                                        this.motY, this.motZ, this.fallDistance }, new boolean[] { this.positionChanged, this.E, this.onGround, this.F }));
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







    protected boolean s_() {
        return false;
    }

    protected void h() {}

    public boolean ad() {
        return !this.dead;
    }

    //TODO ADDED CODE
    public void t_() {
        Block block = this.block.getBlock();
        if (block.getMaterial() == Material.AIR) {
            die();
        } else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            if (this.ticksLived++ == 0) {
                BlockPosition blockposition = new BlockPosition(this);
                if (this.world.getType(blockposition).getBlock() == block && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.AIR, 0).isCancelled()) {
                    this.world.setAir(blockposition);
                    this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
                } else if (!this.world.isClientSide) {
                    die();
                    return;
                }
            }
            this.motY -= 0.03999999910593033D;
            move(this.motX, this.motY, this.motZ);
            this.world.movement++;
            if (this.inUnloadedChunk && this.world.paperSpigotConfig.removeUnloadedFallingBlocks)
                die();
            if (this.world.paperSpigotConfig.fallingBlockHeightNerf != 0 && this.locY > this.world.paperSpigotConfig.fallingBlockHeightNerf) {
                if (this.dropItem)
                    a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                die();
            }
            this.motX *= 0.9800000190734863D;
            this.motY *= 0.9800000190734863D;
            this.motZ *= 0.9800000190734863D;
            if (!this.world.isClientSide)
                if (this.onGround) {
                    BlockPosition blockposition = new BlockPosition(this);
                    this.motX *= 0.699999988079071D;
                    this.motZ *= 0.699999988079071D;
                    this.motY *= -0.5D;
                    if (this.world.getType(blockposition).getBlock() != Blocks.PISTON_EXTENSION) {
                        die();
                        if (!this.e)
                            if (this.world.a(block, blockposition, true, EnumDirection.UP, null, null) && !BlockFalling.canFall(this.world, blockposition.down()) && this.world.getType(blockposition) != this.block) {
                                if (CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.block.getBlock(), this.block.getBlock().toLegacyData(this.block)).isCancelled())
                                    return;
                                this.world.setTypeAndData(blockposition, this.block, 3);
                                this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
                                if (block instanceof BlockFalling)
                                    ((BlockFalling)block).a_(this.world, blockposition);
                                if (this.tileEntityData != null && block instanceof IContainer) {
                                    TileEntity tileentity = this.world.getTileEntity(blockposition);
                                    if (tileentity != null) {
                                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                                        tileentity.b(nbttagcompound);
                                        for (String s : this.tileEntityData.c()) {
                                            NBTBase nbtbase = this.tileEntityData.get(s);
                                            if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
                                                nbttagcompound.set(s, nbtbase.clone());
                                        }
                                        tileentity.a(nbttagcompound);
                                        tileentity.update();
                                    }
                                }
                            } else if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                                a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                            }
                    } else if (this.ticksLived >= Settings.IMP.SETTINGS.PERFORMANCE.TNT.TICKS_BEFORE_FALLING_BLOCK_36_ARE_REMOVED) {
                        if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
                            a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                        die();
                    }
                } else if ((this.ticksLived > 100 && (this.locY < 1.0D || this.locY > this.world.paperSpigotConfig.fallingBlockHeightNerf)) || this.ticksLived > 600) {
                    if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
                        a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                    die();
                }
        }
    }
    //TODO ADDED CODE


    //TODO OLD CODE
    /*
    public void t_() {
        Block block = this.block.getBlock();

        if (block.getMaterial() == Material.AIR) {
            this.die();
        } else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            BlockPosition blockposition;

            if (this.ticksLived++ == 0) {
                blockposition = new BlockPosition(this);
                if (this.world.getType(blockposition).getBlock() == block && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.AIR, 0).isCancelled()) {
                    this.world.setAir(blockposition);
                    world.spigotConfig.antiXrayInstance.updateNearbyBlocks(world, blockposition); // Spigot
                } else if (!this.world.isClientSide) {
                    this.die();
                    return;
                }
            }

            this.motY -= 0.03999999910593033D;
            this.move(this.motX, this.motY, this.motZ);

            // PaperSpigot start - Remove entities in unloaded chunks
            if (this.inUnloadedChunk && world.paperSpigotConfig.removeUnloadedFallingBlocks) {
                this.die();
            }
            // PaperSpigot end

            // PaperSpigot start - Drop falling blocks above the specified height
            if (this.world.paperSpigotConfig.fallingBlockHeightNerf != 0 && this.locY > this.world.paperSpigotConfig.fallingBlockHeightNerf) {
                if (this.dropItem) {
                    this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                }

                this.die();
            }
            // PaperSpigot end

            this.motX *= 0.9800000190734863D;
            this.motY *= 0.9800000190734863D;
            this.motZ *= 0.9800000190734863D;
            if (!this.world.isClientSide) {
                blockposition = new BlockPosition(this);
                if (this.onGround) {
                    this.motX *= 0.699999988079071D;
                    this.motZ *= 0.699999988079071D;
                    this.motY *= -0.5D;
                    if (this.world.getType(blockposition).getBlock() != Blocks.PISTON_EXTENSION) {
                        this.die();
                        if (!this.e) {
                            if (this.world.a(block, blockposition, true, EnumDirection.UP, null, null) && !BlockFalling.canFall(this.world, blockposition.down()) /* mimic the false conditions of setTypeIdAndData */ /*&& blockposition.getX() >= -30000000 && blockposition.getZ() >= -30000000 && blockposition.getX() < 30000000 && blockposition.getZ() < 30000000 && blockposition.getY() >= 0 && blockposition.getY() < 256 && this.world.getType(blockposition) != this.block) {
                                if (CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.block.getBlock(), this.block.getBlock().toLegacyData(this.block)).isCancelled()) {
                                    return;
                                }
                                this.world.setTypeAndData(blockposition, this.block, 3);
                                world.spigotConfig.antiXrayInstance.updateNearbyBlocks(world, blockposition); // Spigot
                                // CraftBukkit end
                                if (block instanceof BlockFalling) {
                                    ((BlockFalling) block).a_(this.world, blockposition);
                                }

                                if (this.tileEntityData != null && block instanceof IContainer) {
                                    TileEntity tileentity = this.world.getTileEntity(blockposition);

                                    if (tileentity != null) {
                                        NBTTagCompound nbttagcompound = new NBTTagCompound();

                                        tileentity.b(nbttagcompound);
                                        Iterator iterator = this.tileEntityData.c().iterator();

                                        while (iterator.hasNext()) {
                                            String s = (String) iterator.next();
                                            NBTBase nbtbase = this.tileEntityData.get(s);

                                            if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                                                nbttagcompound.set(s, nbtbase.clone());
                                            }
                                        }

                                        tileentity.a(nbttagcompound);
                                        tileentity.update();
                                    }
                                }
                            } else if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                                this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                            }
                        }
                    }
                } else if (this.ticksLived > 100 && !this.world.isClientSide && (blockposition.getY() < 1 || blockposition.getY() > 256) || this.ticksLived > 600) {
                    if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                    }

                    this.die();
                }
            }

        }
    }
    */
    //TODO OLD CODE

    public void e(float f, float f1) {
        Block block = this.block.getBlock();

        if (this.hurtEntities) {
            int i = MathHelper.f(f - 1.0F);

            if (i > 0) {
                ArrayList arraylist = Lists.newArrayList(this.world.getEntities(this, this.getBoundingBox()));
                boolean flag = block == Blocks.ANVIL;
                DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();

                    CraftEventFactory.entityDamage = this; // CraftBukkit
                    entity.damageEntity(damagesource, (float) Math.min(MathHelper.d((float) i * this.fallHurtAmount), this.fallHurtMax));
                    CraftEventFactory.entityDamage = null; // CraftBukkit
                }

                if (flag && (double) this.random.nextFloat() < 0.05000000074505806D + (double) i * 0.05D) {
                    int j = this.block.get(BlockAnvil.DAMAGE).intValue();

                    ++j;
                    if (j > 2) {
                        this.e = true;
                    } else {
                        this.block = this.block.set(BlockAnvil.DAMAGE, Integer.valueOf(j));
                    }
                }
            }
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        Block block = this.block != null ? this.block.getBlock() : Blocks.AIR;
        MinecraftKey minecraftkey = Block.REGISTRY.c(block);

        nbttagcompound.setString("Block", minecraftkey == null ? "" : minecraftkey.toString());
        nbttagcompound.setByte("Data", (byte) block.toLegacyData(this.block));
        nbttagcompound.setByte("Time", (byte) this.ticksLived);
        nbttagcompound.setBoolean("DropItem", this.dropItem);
        nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
        nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        nbttagcompound.setInt("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            nbttagcompound.set("TileEntityData", this.tileEntityData);
        }
        // PaperSpigot start - Add FallingBlock source location API
        if (sourceLoc != null) {
            nbttagcompound.setInt("SourceLoc_x", sourceLoc.getBlockX());
            nbttagcompound.setInt("SourceLoc_y", sourceLoc.getBlockY());
            nbttagcompound.setInt("SourceLoc_z", sourceLoc.getBlockZ());
        }
        // PaperSpigot end
    }

    protected void a(NBTTagCompound nbttagcompound) {
        int i = nbttagcompound.getByte("Data") & 255;

        if (nbttagcompound.hasKeyOfType("Block", 8)) {
            this.block = Block.getByName(nbttagcompound.getString("Block")).fromLegacyData(i);
        } else if (nbttagcompound.hasKeyOfType("TileID", 99)) {
            this.block = Block.getById(nbttagcompound.getInt("TileID")).fromLegacyData(i);
        } else {
            this.block = Block.getById(nbttagcompound.getByte("Tile") & 255).fromLegacyData(i);
        }

        this.ticksLived = nbttagcompound.getByte("Time") & 255;
        Block block = this.block.getBlock();

        if (nbttagcompound.hasKeyOfType("HurtEntities", 99)) {
            this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
            this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
            this.fallHurtMax = nbttagcompound.getInt("FallHurtMax");
        } else if (block == Blocks.ANVIL) {
            this.hurtEntities = true;
        }

        if (nbttagcompound.hasKeyOfType("DropItem", 99)) {
            this.dropItem = nbttagcompound.getBoolean("DropItem");
        }

        if (nbttagcompound.hasKeyOfType("TileEntityData", 10)) {
            this.tileEntityData = nbttagcompound.getCompound("TileEntityData");
        }

        if (block == null || block.getMaterial() == Material.AIR) {
            this.block = Blocks.SAND.getBlockData();
        }
        // PaperSpigot start - Add FallingBlock source location API
        if (nbttagcompound.hasKey("SourceLoc_x")) {
            int srcX = nbttagcompound.getInt("SourceLoc_x");
            int srcY = nbttagcompound.getInt("SourceLoc_y");
            int srcZ = nbttagcompound.getInt("SourceLoc_z");
            sourceLoc = new org.bukkit.Location(world.getWorld(), srcX, srcY, srcZ);
        }
        // PaperSpigot end
    }

    public void a(boolean flag) {
        this.hurtEntities = flag;
    }

    public void appendEntityCrashDetails(CrashReportSystemDetails crashreportsystemdetails) {
        super.appendEntityCrashDetails(crashreportsystemdetails);
        if (this.block != null) {
            Block block = this.block.getBlock();

            crashreportsystemdetails.a("Immitating block ID", Block.getId(block));
            crashreportsystemdetails.a("Immitating block data", block.toLegacyData(this.block));
        }

    }

    public IBlockData getBlock() {
        return this.block;
    }

    // PaperSpigot start - Fix cannons
    @Override
    public double f(double d0, double d1, double d2) {
        if (!world.paperSpigotConfig.fixCannons) return super.f(d0, d1, d2);

        double d3 = this.locX - d0;
        double d4 = this.locY + this.getHeadHeight() - d1;
        double d5 = this.locZ - d2;

        //TODO OLD CODE
        //return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        //TODO OLD CODE

        //TODO ADDED CODE
        return FastMath.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        //TODO ADDED CODE

    }

    @Override
    public float getHeadHeight() {
        return world.paperSpigotConfig.fixCannons ? this.length / 2 : super.getHeadHeight();
    }
    // PaperSpigot end
}
