package net.minecraft.server;

import eu.revamp.spigot.events.plants.CactusBreakEvent;
import org.bukkit.craftbukkit.event.CraftEventFactory;

import java.util.Random;

public class BlockCactus extends Block {

    public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);

    protected BlockCactus() {
        super(Material.CACTUS);
        this.j(this.blockStateList.getBlockData().set(BlockCactus.AGE, 0));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        BlockPosition blockposition1 = blockposition.up();

        if (world.isEmpty(blockposition1)) {
            int i;

            for (i = 1; world.getType(blockposition.down(i)).getBlock() == this; ++i) {
            }

            if (i < world.paperSpigotConfig.cactusMaxHeight) { // PaperSpigot - Configurable max growth height for cactus blocks) {
                int j = iblockdata.get(BlockCactus.AGE);

                if (j >= (byte) range(3, (world.growthOdds / world.spigotConfig.cactusModifier * 15) + 0.5F, 15)) { // Spigot
                    // world.setTypeUpdate(blockposition1, this.getBlockData()); // CraftBukkit
                    IBlockData iblockdata1 = iblockdata.set(BlockCactus.AGE, 0);

                    CraftEventFactory.handleBlockGrowEvent(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), this, 0); // CraftBukkit
                    world.setTypeAndData(blockposition, iblockdata1, 4);
                    this.doPhysics(world, blockposition1, iblockdata1, this);
                } else {
                    world.setTypeAndData(blockposition, iblockdata.set(BlockCactus.AGE, j + 1), 4);
                }

            }
        }
    }

    public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        float f = 0.0625F;

        return new AxisAlignedBB((float) blockposition.getX() + f, blockposition.getY(), (float) blockposition.getZ() + f, (float) (blockposition.getX() + 1) - f, (float) (blockposition.getY() + 1) - f, (float) (blockposition.getZ() + 1) - f);
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return super.canPlace(world, blockposition) && this.e(world, blockposition);
    }

    //TODO OLD CODE
    /*
    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        if (!this.e(world, blockposition)) {
            world.setAir(blockposition, true);
        }

    }
    */
    //TODO OLD CODE

    //TODO ADDED CODE
    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        if (!this.e(world, blockposition)) {
            CactusBreakEvent cactusBreakEvent = new CactusBreakEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
            world.getServer().getPluginManager().callEvent(cactusBreakEvent);
            if (!cactusBreakEvent.isCancelled()) {
                world.setAir(blockposition, true);
            }
        }
    }
    //TODO ADDED CODE


    public boolean e(World world, BlockPosition blockposition) {

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            if (world.getType(blockposition.shift(enumdirection)).getBlock().getMaterial().isBuildable()) {
                return false;
            }
        }

        Block block = world.getType(blockposition.down()).getBlock();

        return block == Blocks.CACTUS || block == Blocks.SAND;
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
        CraftEventFactory.blockDamage = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()); // CraftBukkit
        entity.damageEntity(DamageSource.CACTUS, 1.0F);
        CraftEventFactory.blockDamage = null; // CraftBukkit
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockCactus.AGE, i);
    }

    public int toLegacyData(IBlockData iblockdata) {
        return iblockdata.get(BlockCactus.AGE);
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockCactus.AGE);
    }
}
