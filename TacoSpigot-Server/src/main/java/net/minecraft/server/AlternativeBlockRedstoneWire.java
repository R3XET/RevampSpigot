package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AlternativeBlockRedstoneWire extends BlockRedstoneWire {
    private static final EnumDirection[] facingsHorizontal = new EnumDirection[] { EnumDirection.WEST, EnumDirection.EAST,
            EnumDirection.NORTH, EnumDirection.SOUTH };

    private static final EnumDirection[] facingsVertical = new EnumDirection[] { EnumDirection.DOWN, EnumDirection.UP };

    private static final EnumDirection[] facings = (EnumDirection[])ArrayUtils.addAll(facingsVertical, facingsHorizontal);

    private static final BaseBlockPosition[] surroundingBlocksOffset;

    private final Set<BlockPosition> updatedRedstoneWire;

    private final List<BlockPosition> turnOff;

    private final List<BlockPosition> turnOn;

    private boolean g;

    static {
        Set<BaseBlockPosition> set = Sets.newLinkedHashSet();
        EnumDirection[] arrayOfEnumDirection;
        int i;
        byte b;
        for (i = (arrayOfEnumDirection = facings).length, b = 0; b < i; ) {
            EnumDirection facing = arrayOfEnumDirection[b];
            set.add(getOfT(facing, BaseBlockPosition.class));
            b++;
        }
        for (i = (arrayOfEnumDirection = facings).length, b = 0; b < i; ) {
            EnumDirection facing2 = arrayOfEnumDirection[b];
            BaseBlockPosition v1 = getOfT(facing2, BaseBlockPosition.class);
            EnumDirection[] arrayOfEnumDirection1;
            int j;
            byte b1;
            for (j = (arrayOfEnumDirection1 = facings).length, b1 = 0; b1 < j; ) {
                EnumDirection facing3 = arrayOfEnumDirection1[b1];
                BaseBlockPosition v2 = getOfT(facing3, BaseBlockPosition.class);
                set.add(new BlockPosition(v1.getX() + v2.getX(), v1.getY() + v2.getY(),
                        v1.getZ() + v2.getZ()));
                b1++;
            }
            b++;
        }
        set.remove(BlockPosition.ZERO);
        surroundingBlocksOffset = set.toArray(new BaseBlockPosition[set.size()]);
    }

    public AlternativeBlockRedstoneWire() {
        this.turnOff = Lists.newArrayList();
        this.turnOn = Lists.newArrayList();
        this.updatedRedstoneWire = Sets.newLinkedHashSet();
        this.g = true;
        c(0.0F);
        a(Block.e);
        c("redstoneDust");
        K();
    }

    public static <T> T getOfT(Object obj, Class<T> type) {
        Field[] arrayOfField;
        int i;
        byte b;
        for (i = (arrayOfField = obj.getClass().getDeclaredFields()).length, b = 0; b < i; ) {
            Field field = arrayOfField[b];
            if (type.equals(field.getType()))
                return get(obj, field, type);
            b++;
        }
        return null;
    }

    public static <T> T get(Object obj, Field field, Class<T> type) {
        try {
            field.setAccessible(true);
            return type.cast(field.get(obj));
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> T get(Object obj, String name, Class<T> type) {
        return get(obj, obj.getClass(), name, type);
    }

    public static <T> T get(Object obj, Class<?> clazz, String name, Class<T> type) {
        Field[] arrayOfField;
        int i;
        byte b;
        for (i = (arrayOfField = clazz.getDeclaredFields()).length, b = 0; b < i; ) {
            Field field = arrayOfField[b];
            if (name.equals(field.getName()))
                return get(obj, field, type);
            b++;
        }
        throw new IllegalArgumentException("No field: " + name);
    }

    private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        calculateCurrentChanges(world, blockposition);
        Set<BlockPosition> blocksNeedingUpdate = Sets.newLinkedHashSet();
        for (BlockPosition posi : this.updatedRedstoneWire)
            addBlocksNeedingUpdate(world, posi, blocksNeedingUpdate);
        Iterator<BlockPosition> it = Lists.newLinkedList(this.updatedRedstoneWire)
                .descendingIterator();
        while (it.hasNext())
            addAllSurroundingBlocks(it.next(), blocksNeedingUpdate);
        blocksNeedingUpdate.removeAll(this.updatedRedstoneWire);
        this.updatedRedstoneWire.clear();
        for (BlockPosition posi2 : blocksNeedingUpdate)
            world.d(posi2, this);
    }

    private void calculateCurrentChanges(World world, BlockPosition blockposition) {
        if (world.getType(blockposition).getBlock() == this) {
            this.turnOff.add(blockposition);
        } else {
            checkSurroundingWires(world, blockposition);
        }
        while (!this.turnOff.isEmpty()) {
            int oldPower;
            BlockPosition pos = this.turnOff.remove(0);
            IBlockData state = world.getType(pos);
            if (state == null)
                return;
            try {
                oldPower = state.<Integer>get(POWER);
            } catch (Exception exception) {
                return;
            }
            this.g = false;
            int blockPower = world.A(pos);
            this.g = true;
            int wirePower = getSurroundingWirePower(world, pos);
            wirePower--;
            int newPower = Math.max(blockPower, wirePower);
            if (newPower < oldPower) {
                if (blockPower > 0 && !this.turnOn.contains(pos))
                    this.turnOn.add(pos);
                state = setWireState(world, pos, state, 0);
            } else if (newPower > oldPower) {
                state = setWireState(world, pos, state, newPower);
            }
            checkSurroundingWires(world, pos);
        }
        while (!this.turnOn.isEmpty()) {
            BlockPosition pos = this.turnOn.remove(0);
            IBlockData state = world.getType(pos);
            int oldPower = state.<Integer>get(POWER);
            this.g = false;
            int blockPower = world.A(pos);
            this.g = true;
            int wirePower = getSurroundingWirePower(world, pos);
            wirePower--;
            int newPower = Math.max(blockPower, wirePower);
            if (oldPower != newPower) {
                BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld()
                        .getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), oldPower,
                        newPower);
                world.getServer().getPluginManager().callEvent(event);
                newPower = event.getNewCurrent();
            }
            if (newPower > oldPower)
                state = setWireState(world, pos, state, newPower);
            checkSurroundingWires(world, pos);
        }
        this.turnOff.clear();
        this.turnOn.clear();
    }

    private void addWireToList(World worldIn, BlockPosition pos, int otherPower) {
        IBlockData state = worldIn.getType(pos);
        if (state.getBlock() == this) {
            int power = state.<Integer>get(POWER);
            if (power < otherPower - 1 && !this.turnOn.contains(pos))
                this.turnOn.add(pos);
            if (power > otherPower && !this.turnOff.contains(pos))
                this.turnOff.add(pos);
        }
    }

    private void checkSurroundingWires(World worldIn, BlockPosition pos) {
        IBlockData state = worldIn.getType(pos);
        int ownPower = 0;
        if (state.getBlock() == this)
            ownPower = state.<Integer>get(POWER);
        EnumDirection[] arrayOfEnumDirection;
        int i;
        byte b;
        for (i = (arrayOfEnumDirection = facingsHorizontal).length, b = 0; b < i; ) {
            EnumDirection facing = arrayOfEnumDirection[b];
            BlockPosition offsetPos = pos.shift(facing);
            if (facing.k().c())
                addWireToList(worldIn, offsetPos, ownPower);
            b++;
        }
        for (i = (arrayOfEnumDirection = facingsVertical).length, b = 0; b < i; ) {
            EnumDirection facingVertical = arrayOfEnumDirection[b];
            BlockPosition offsetPos = pos.shift(facingVertical);
            boolean solidBlock = worldIn.getType(offsetPos).getBlock().u();
            EnumDirection[] arrayOfEnumDirection1;
            int j;
            byte b1;
            for (j = (arrayOfEnumDirection1 = facingsHorizontal).length, b1 = 0; b1 < j; ) {
                EnumDirection facingHorizontal = arrayOfEnumDirection1[b1];
                if ((facingVertical == EnumDirection.UP && !solidBlock) || (
                        facingVertical == EnumDirection.DOWN && solidBlock &&
                                !worldIn.getType(offsetPos.shift(facingHorizontal)).getBlock().isOccluding()))
                    addWireToList(worldIn, offsetPos.shift(facingHorizontal), ownPower);
                b1++;
            }
            b++;
        }
    }

    private int getSurroundingWirePower(World worldIn, BlockPosition pos) {
        int wirePower = 0;
        for (EnumDirection enumfacing : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            BlockPosition offsetPos = pos.shift(enumfacing);
            wirePower = getPower(worldIn, offsetPos, wirePower);
            if (worldIn.getType(offsetPos).getBlock().isOccluding() &&
                    !worldIn.getType(pos.up()).getBlock().isOccluding()) {
                wirePower = getPower(worldIn, offsetPos.up(), wirePower);
                continue;
            }
            if (worldIn.getType(offsetPos).getBlock().isOccluding())
                continue;
            wirePower = getPower(worldIn, offsetPos.down(), wirePower);
        }
        return wirePower;
    }

    private void addBlocksNeedingUpdate(World worldIn, BlockPosition pos, Set<BlockPosition> set) {
        List<EnumDirection> connectedSides = getSidesToPower(worldIn, pos);
        EnumDirection[] arrayOfEnumDirection;
        int i;
        byte b;
        for (i = (arrayOfEnumDirection = facings).length, b = 0; b < i; ) {
            EnumDirection facing = arrayOfEnumDirection[b];
            BlockPosition offsetPos = pos.shift(facing);
            if ((connectedSides.contains(facing.opposite()) || facing == EnumDirection.DOWN || (
                    facing.k().c() && a(worldIn.getType(offsetPos), facing))) &&
                    canBlockBePoweredFromSide(worldIn.getType(offsetPos), facing, true))
                set.add(offsetPos);
            b++;
        }
        for (i = (arrayOfEnumDirection = facings).length, b = 0; b < i; ) {
            EnumDirection facing = arrayOfEnumDirection[b];
            BlockPosition offsetPos = pos.shift(facing);
            if ((connectedSides.contains(facing.opposite()) || facing == EnumDirection.DOWN) && worldIn
                    .getType(offsetPos).getBlock().isOccluding()) {
                EnumDirection[] arrayOfEnumDirection1;
                int j;
                byte b1;
                for (j = (arrayOfEnumDirection1 = facings).length, b1 = 0; b1 < j; ) {
                    EnumDirection facing2 = arrayOfEnumDirection1[b1];
                    if (canBlockBePoweredFromSide(worldIn.getType(offsetPos.shift(facing2)), facing2,
                            false))
                        set.add(offsetPos.shift(facing2));
                    b1++;
                }
            }
            b++;
        }
    }

    private boolean canBlockBePoweredFromSide(IBlockData state, EnumDirection side, boolean isWire) {
        if (state.getBlock() instanceof BlockPiston &&
                state.get(BlockPiston.FACING) == side.opposite())
            return false;
        if (state.getBlock() instanceof BlockDiodeAbstract &&
                state.get(BlockDiodeAbstract.FACING) != side.opposite())
            return (isWire && state.getBlock() instanceof BlockRedstoneComparator && state.get(BlockRedstoneComparator.FACING).k() != side
                    .k() && side.k().c());
        return !(state.getBlock() instanceof BlockRedstoneTorch && (isWire ||
                state.get(BlockRedstoneTorch.FACING) != side));
    }

    private List<EnumDirection> getSidesToPower(World worldIn, BlockPosition pos) {
        List<EnumDirection> retval = Lists.newArrayList();
        EnumDirection[] arrayOfEnumDirection;
        int i;
        byte b;
        for (i = (arrayOfEnumDirection = facingsHorizontal).length, b = 0; b < i; ) {
            EnumDirection facing = arrayOfEnumDirection[b];
            if (d(worldIn, pos, facing))
                retval.add(facing);
            b++;
        }
        if (retval.isEmpty())
            return Lists.newArrayList(facingsHorizontal);
        boolean northsouth =
                !(!retval.contains(EnumDirection.NORTH) && !retval.contains(EnumDirection.SOUTH));
        boolean eastwest =
                !(!retval.contains(EnumDirection.EAST) && !retval.contains(EnumDirection.WEST));
        if (northsouth) {
            retval.remove(EnumDirection.EAST);
            retval.remove(EnumDirection.WEST);
        }
        if (eastwest) {
            retval.remove(EnumDirection.NORTH);
            retval.remove(EnumDirection.SOUTH);
        }
        return retval;
    }

    private void addAllSurroundingBlocks(BlockPosition pos, Set<BlockPosition> set) {
        BaseBlockPosition[] arrayOfBaseBlockPosition;
        int i;
        byte b;
        for (i = (arrayOfBaseBlockPosition = surroundingBlocksOffset).length, b = 0; b < i; ) {
            BaseBlockPosition vect = arrayOfBaseBlockPosition[b];
            set.add(pos.a(vect));
            b++;
        }
    }

    private IBlockData setWireState(World worldIn, BlockPosition pos, IBlockData state, int power) {
        state = state.set(POWER, Integer.valueOf(power));
        worldIn.setTypeAndData(pos, state, 2);
        this.updatedRedstoneWire.add(pos);
        return state;
    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!world.isClientSide) {
            e(world, blockposition, iblockdata);
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.VERTICAL)
                world.applyPhysics(blockposition.shift(enumdirection), this);
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                b(world, blockposition.shift(enumdirection));
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                BlockPosition blockposition2 = blockposition.shift(enumdirection);
                if (world.getType(blockposition2).getBlock().isOccluding()) {
                    b(world, blockposition2.up());
                    continue;
                }
                b(world, blockposition2.down());
            }
        }
    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        super.remove(world, blockposition, iblockdata);
        if (!world.isClientSide) {
            EnumDirection[] arrayOfEnumDirection;
            int i;
            byte b;
            for (i = (arrayOfEnumDirection = EnumDirection.values()).length, b = 0; b < i; ) {
                EnumDirection enumdirection = arrayOfEnumDirection[b];
                world.applyPhysics(blockposition.shift(enumdirection), this);
                b++;
            }
            e(world, blockposition, iblockdata);
            for (EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                b(world, blockposition.shift(enumdirection2));
            for (EnumDirection enumdirection2 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                BlockPosition blockposition2 = blockposition.shift(enumdirection2);
                if (world.getType(blockposition2).getBlock().isOccluding()) {
                    b(world, blockposition2.up());
                    continue;
                }
                b(world, blockposition2.down());
            }
        }
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        if (!world.isClientSide)
            if (canPlace(world, blockposition)) {
                e(world, blockposition, iblockdata);
            } else {
                b(world, blockposition, iblockdata, 0);
                world.setAir(blockposition);
            }
    }

    public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
        if (!this.g)
            return 0;
        int i = iblockdata.<Integer>get(BlockRedstoneWire.POWER);
        if (i == 0)
            return 0;
        if (enumdirection == EnumDirection.UP)
            return i;
        if (getSidesToPower((World)iblockaccess, blockposition).contains(enumdirection))
            return i;
        return 0;
    }

    private boolean d(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        BlockPosition blockposition2 = blockposition.shift(enumdirection);
        IBlockData iblockdata = iblockaccess.getType(blockposition2);
        Block block = iblockdata.getBlock();
        boolean flag = block.isOccluding();
        boolean flag2 = iblockaccess.getType(blockposition.up()).getBlock().isOccluding();
        return !((flag2 || !flag || !e(iblockaccess, blockposition2.up())) && !a(iblockdata, enumdirection) && (
                block != Blocks.POWERED_REPEATER ||
                        iblockdata.get(BlockDiodeAbstract.FACING) != enumdirection) && (flag || !e(
                iblockaccess, blockposition2.down())));
    }
}

