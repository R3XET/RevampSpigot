package net.minecraft.server;

import com.google.common.base.Predicate;
import net.jafama.FastMath;
import org.bukkit.craftbukkit.event.CraftEventFactory;

import java.util.List;
import java.util.Random;

public class BlockRedstoneComparator extends BlockDiodeAbstract implements IContainer {
    public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
    public static final BlockStateEnum<EnumComparatorMode> MODE = BlockStateEnum.of("mode", EnumComparatorMode.class);

    public BlockRedstoneComparator(boolean var1) {
        super(var1);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, false).set(MODE, EnumComparatorMode.COMPARE));
        this.isTileEntity = true;
    }

    @Override
    public String getName() {
        return LocaleI18n.get("item.comparator.name");
    }

    @Override
    public Item getDropType(IBlockData var1, Random var2, int var3) {
        return Items.COMPARATOR;
    }

    @Override
    protected int d(IBlockData var1) {
        return 2;
    }

    @Override
    protected IBlockData e(IBlockData var1) {
        Boolean var2 = var1.get(POWERED);
        EnumComparatorMode var3 = var1.get(MODE);
        EnumDirection var4 = var1.get(FACING);
        return Blocks.POWERED_COMPARATOR.getBlockData().set(FACING, var4).set(POWERED, var2).set(MODE, var3);
    }

    @Override
    protected IBlockData k(IBlockData var1) {
        Boolean var2 = var1.get(POWERED);
        EnumComparatorMode var3 = var1.get(MODE);
        EnumDirection var4 = var1.get(FACING);
        return Blocks.UNPOWERED_COMPARATOR.getBlockData().set(FACING, var4).set(POWERED, var2).set(MODE, var3);
    }

    @Override
    protected boolean l(IBlockData var1) {
        return this.N || var1.get(POWERED) != false;
    }

    @Override
    protected int a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        TileEntity var4 = var1.getTileEntity(var2);
        return var4 instanceof TileEntityComparator ? ((TileEntityComparator)var4).b() : 0;
    }

    private int j(World var1, BlockPosition var2, IBlockData var3) {
        return var3.get(MODE) == EnumComparatorMode.SUBTRACT ? FastMath.max(this.f(var1, var2, var3) - this.c(var1, var2, var3), 0) : this.f(var1, var2, var3);
    }

    @Override
    protected boolean e(World var1, BlockPosition var2, IBlockData var3) {
        int var4 = this.f(var1, var2, var3);
        if (var4 >= 15) {
            return true;
        }
        if (var4 == 0) {
            return false;
        }
        int var5 = this.c(var1, var2, var3);
        if (var5 == 0) {
            return true;
        }
        return var4 >= var5;
    }

    @Override
    protected int f(World var1, BlockPosition var2, IBlockData var3) {
        int var4 = super.f(var1, var2, var3);
        EnumDirection var5 = var3.get(FACING);
        BlockPosition var6 = var2.shift(var5);
        Block var7 = var1.getType(var6).getBlock();
        if (var7.isComplexRedstone()) {
            var4 = var7.l(var1, var6);
        } else if (var4 < 15 && var7.isOccluding()) {
            EntityItemFrame var8;
            var7 = var1.getType(var6 = var6.shift(var5)).getBlock();
            if (var7.isComplexRedstone()) {
                var4 = var7.l(var1, var6);
            } else if (var7.getMaterial() == Material.AIR && (var8 = this.a(var1, var5, var6)) != null) {
                var4 = var8.q();
            }
        }
        return var4;
    }

    private EntityItemFrame a(World var1, EnumDirection var2, BlockPosition var3) {
        List<EntityItemFrame> var4 = var1.a(EntityItemFrame.class, new AxisAlignedBB(var3.getX(), var3.getY(), var3.getZ(), (var3.getX() + 1), (var3.getY() + 1), (var3.getZ() + 1)), (Predicate<? super EntityItemFrame>) var11 ->
                (var11 != null && var11.getDirection() == var2));
        return (var4.size() == 1) ? var4.get(0) : null;
    }

    @Override
    public boolean interact(World var1, BlockPosition var2, IBlockData var3, EntityHuman var4, EnumDirection var5, float var6, float var7, float var8) {
        if (!var4.abilities.mayBuild) {
            return false;
        }
        var1.makeSound((double)var2.getX() + 0.5, (double)var2.getY() + 0.5, (double)var2.getZ() + 0.5, "random.clock", 0.3f, (var3 = var3.a(MODE)).get(MODE) == EnumComparatorMode.SUBTRACT ? 0.55f : 0.5f);
        var1.setTypeAndData(var2, var3, 2);
        this.k(var1, var2, var3);
        return true;
    }

    @Override
    protected void g(World var1, BlockPosition var2, IBlockData var3) {
        if (!var1.a(var2, this)) {
            int var6;
            int var4 = this.j(var1, var2, var3);
            TileEntity var5 = var1.getTileEntity(var2);
            int n = var6 = var5 instanceof TileEntityComparator ? ((TileEntityComparator)var5).b() : 0;
            if (var4 != var6 || this.l(var3) != this.e(var1, var2, var3)) {
                if (this.i(var1, var2, var3)) {
                    var1.a(var2, this, 2, -1);
                } else {
                    var1.a(var2, this, 2, 0);
                }
            }
        }
    }

    private void k(World var1, BlockPosition var2, IBlockData var3) {
        int var4 = this.j(var1, var2, var3);
        TileEntity var5 = var1.getTileEntity(var2);
        int var6 = 0;
        if (var5 instanceof TileEntityComparator) {
            TileEntityComparator var7 = (TileEntityComparator)var5;
            var6 = var7.b();
            var7.a(var4);
        }
        if (var6 != var4 || var3.get(MODE) == EnumComparatorMode.COMPARE) {
            boolean var9 = this.e(var1, var2, var3);
            boolean var8 = this.l(var3);
            if (var8 && !var9) {
                if (CraftEventFactory.callRedstoneChange(var1, var2.getX(), var2.getY(), var2.getZ(), 15, 0).getNewCurrent() != 0) {
                    return;
                }
                var1.setTypeAndData(var2, var3.set(POWERED, false), 2);
            } else if (!var8 && var9) {
                if (CraftEventFactory.callRedstoneChange(var1, var2.getX(), var2.getY(), var2.getZ(), 0, 15).getNewCurrent() != 15) {
                    return;
                }
                var1.setTypeAndData(var2, var3.set(POWERED, true), 2);
            }
            this.h(var1, var2, var3);
        }
    }

    @Override
    public void b(World var1, BlockPosition var2, IBlockData var3, Random var4) {
        if (this.N) {
            var1.setTypeAndData(var2, this.k(var3).set(POWERED, true), 4);
        }
        this.k(var1, var2, var3);
    }

    @Override
    public void onPlace(World var1, BlockPosition var2, IBlockData var3) {
        super.onPlace(var1, var2, var3);
        var1.setTileEntity(var2, this.a(var1, 0));
    }

    @Override
    public void remove(World var1, BlockPosition var2, IBlockData var3) {
        super.remove(var1, var2, var3);
        var1.t(var2);
        this.h(var1, var2, var3);
    }

    @Override
    public boolean a(World var1, BlockPosition var2, IBlockData var3, int var4, int var5) {
        super.a(var1, var2, var3, var4, var5);
        TileEntity var6 = var1.getTileEntity(var2);
        return var6 != null && var6.c(var4, var5);
    }

    @Override
    public TileEntity a(World var1, int var2) {
        return new TileEntityComparator();
    }

    @Override
    public IBlockData fromLegacyData(int var1) {
        return this.getBlockData().set(FACING, EnumDirection.fromType2(var1)).set(POWERED, (var1 & 8) > 0).set(MODE, (var1 & 4) > 0 ? EnumComparatorMode.SUBTRACT : EnumComparatorMode.COMPARE);
    }

    @Override
    public int toLegacyData(IBlockData var1) {
        int var2 = 0;
        int var3 = var2 | var1.get(FACING).b();
        if (var1.get(POWERED).booleanValue()) {
            var3 |= 8;
        }
        if (var1.get(MODE) == EnumComparatorMode.SUBTRACT) {
            var3 |= 4;
        }
        return var3;
    }

    @Override
    protected BlockStateList getStateList() {
        return new BlockStateList(this, FACING, MODE, POWERED);
    }

    @Override
    public IBlockData getPlacedState(World var1, BlockPosition var2, EnumDirection var3, float var4, float var5, float var6, int var7, EntityLiving var8) {
        return this.getBlockData().set(FACING, var8.getDirection().opposite()).set(POWERED, false).set(MODE, EnumComparatorMode.COMPARE);
    }

    public enum EnumComparatorMode implements INamable
    {
        COMPARE("compare"),
        SUBTRACT("subtract");

        private final String c;

        EnumComparatorMode(String var3) {
            this.c = var3;
        }

        public String toString() {
            return this.c;
        }

        @Override
        public String getName() {
            return this.c;
        }
    }

}

