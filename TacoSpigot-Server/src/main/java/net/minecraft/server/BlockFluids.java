package net.minecraft.server;

import eu.revamp.spigot.config.Settings;

import java.util.Iterator;
import java.util.Random;

public abstract class BlockFluids extends Block {

    public static final BlockStateInteger LEVEL = BlockStateInteger.of("level", 0, 15);

    protected BlockFluids(Material material) {
        super(material);
        this.j(this.blockStateList.getBlockData().set(BlockFluids.LEVEL, 0));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.a(true);
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return this.material != Material.LAVA;
    }

    public static float b(int i) {
        if (i >= 8) {
            i = 0;
        }

        return (float) (i + 1) / 9.0F;
    }

    protected int e(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition).getBlock().getMaterial() == this.material ? iblockaccess.getType(blockposition).get(BlockFluids.LEVEL) : -1;
    }

    protected int f(IBlockAccess iblockaccess, BlockPosition blockposition) {
        int i = this.e(iblockaccess, blockposition);

        return i >= 8 ? 0 : i;
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public boolean a(IBlockData iblockdata, boolean flag) {
        return flag && iblockdata.get(BlockFluids.LEVEL) == 0;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        Material material = iblockaccess.getType(blockposition).getBlock().getMaterial();

        return material != this.material && (enumdirection == EnumDirection.UP || (material != Material.ICE && super.b(iblockaccess, blockposition, enumdirection)));
    }

    public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return null;
    }

    public int b() {
        return 1;
    }

    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    protected Vec3D h(IBlockAccess iblockaccess, BlockPosition blockposition) {
        Vec3D vec3d = new Vec3D(0.0D, 0.0D, 0.0D);
        int i = this.f(iblockaccess, blockposition);
        Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

        EnumDirection enumdirection;
        BlockPosition blockposition1;

        while (iterator.hasNext()) {
            enumdirection = (EnumDirection) iterator.next();
            blockposition1 = blockposition.shift(enumdirection);
            int j = this.f(iblockaccess, blockposition1);
            int k;

            if (j < 0) {
                if (!iblockaccess.getType(blockposition1).getBlock().getMaterial().isSolid()) {
                    j = this.f(iblockaccess, blockposition1.down());
                    if (j >= 0) {
                        k = j - (i - 8);
                        vec3d = vec3d.add((blockposition1.getX() - blockposition.getX()) * k, (blockposition1.getY() - blockposition.getY()) * k, (blockposition1.getZ() - blockposition.getZ()) * k);
                    }
                }
            } else if (j >= 0) {
                k = j - i;
                vec3d = vec3d.add((blockposition1.getX() - blockposition.getX()) * k, (blockposition1.getY() - blockposition.getY()) * k, (blockposition1.getZ() - blockposition.getZ()) * k);
            }
        }

        if (iblockaccess.getType(blockposition).get(BlockFluids.LEVEL) >= 8) {
            iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumdirection = (EnumDirection) iterator.next();
                blockposition1 = blockposition.shift(enumdirection);
                if (this.b(iblockaccess, blockposition1, enumdirection) || this.b(iblockaccess, blockposition1.up(), enumdirection)) {
                    vec3d = vec3d.a().add(0.0D, -6.0D, 0.0D);
                    break;
                }
            }
        }

        return vec3d.a();
    }

    public Vec3D a(World world, BlockPosition blockposition, Entity entity, Vec3D vec3d) {
        return vec3d.e(this.h(world, blockposition));
    }

    public int a(World world) {
        return this.material == Material.WATER ? 5 : (this.material == Material.LAVA ? (world.worldProvider.o() ? 10 : 30) : 0);
    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        this.e(world, blockposition, iblockdata);
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        this.e(world, blockposition, iblockdata);
    }

    public boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (this.material == Material.LAVA) {
            boolean flag = false;
            EnumDirection[] aenumdirection = EnumDirection.values();
            int i = aenumdirection.length;

            for (EnumDirection enumdirection : aenumdirection) {
                if (enumdirection != EnumDirection.DOWN && world.getType(blockposition.shift(enumdirection)).getBlock().getMaterial() == Material.WATER) {
                    flag = true;
                    break;
                }
            }

            //TODO ADDED CODE
            if (flag && Settings.IMP.SETTINGS.PERFORMANCE.LAVA_TO_COBBLE) {
                Integer integer = iblockdata.<Integer>get(LEVEL);
                if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.USE_OLD_LIQUID_FLOW_MECHANICS) {
                    if (integer == 0) {
                        world.setTypeUpdate(blockposition, Blocks.OBSIDIAN.getBlockData());
                        fizz(world, blockposition);
                        return true;
                    }
                    if (integer <= 4) {
                        world.setTypeUpdate(blockposition, Blocks.COBBLESTONE.getBlockData());
                        fizz(world, blockposition);
                        return true;
                    }
                } else if (integer >= 0) {
                    world.setTypeUpdate(blockposition, ((integer == 0) ? Blocks.OBSIDIAN : Blocks.COBBLESTONE).getBlockData());
                    fizz(world, blockposition);
                    return true;
                }
            }
            //TODO ADDED CODE


            //TODO OLD CODE
            /*
            if (flag) {
                Integer integer = iblockdata.get(BlockFluids.LEVEL);

                if (integer == 0) {
                    world.setTypeUpdate(blockposition, Blocks.OBSIDIAN.getBlockData());
                    this.fizz(world, blockposition);
                    return true;
                }

                if (integer > 0) { // PaperSpigot
                    world.setTypeUpdate(blockposition, Blocks.COBBLESTONE.getBlockData());
                    this.fizz(world, blockposition);
                    return true;
                }
            }
             */
            //TODO OLD CODE
        }

        return false;
    }

    protected void fizz(World world, BlockPosition blockposition) {
        double d0 = blockposition.getX();
        double d1 = blockposition.getY();
        double d2 = blockposition.getZ();

        world.makeSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

        for (int i = 0; i < 8; ++i) {
            world.addParticle(EnumParticle.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D);
        }

    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockFluids.LEVEL, i);
    }

    public int toLegacyData(IBlockData iblockdata) {
        return iblockdata.get(BlockFluids.LEVEL);
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockFluids.LEVEL);
    }

    public static BlockFlowing a(Material material) {
        if (material == Material.WATER) {
            return Blocks.FLOWING_WATER;
        } else if (material == Material.LAVA) {
            return Blocks.FLOWING_LAVA;
        } else {
            throw new IllegalArgumentException("Invalid material");
        }
    }

    public static BlockStationary b(Material material) {
        if (material == Material.WATER) {
            return Blocks.WATER;
        } else if (material == Material.LAVA) {
            return Blocks.LAVA;
        } else {
            throw new IllegalArgumentException("Invalid material");
        }
    }
}
