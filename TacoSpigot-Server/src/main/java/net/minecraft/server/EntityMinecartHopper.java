package net.minecraft.server;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
    private boolean a = true;

    private int b = -1;

    private BlockPosition c = BlockPosition.ZERO;

    public EntityMinecartHopper(World paramWorld) {
        super(paramWorld);
    }

    public EntityMinecartHopper(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
        super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
    }

    public EntityMinecartAbstract.EnumMinecartType s() {
        return EntityMinecartAbstract.EnumMinecartType.HOPPER;
    }

    public IBlockData u() {
        return Blocks.HOPPER.getBlockData();
    }

    public int w() {
        return 1;
    }

    public int getSize() {
        return 5;
    }

    public boolean e(EntityHuman paramEntityHuman) {
        if (!this.world.isClientSide)
            paramEntityHuman.openContainer(this);
        return true;
    }

    public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
        boolean bool = !paramBoolean;
        if (bool != y())
            i(bool);
    }

    public boolean y() {
        return this.a;
    }

    public void i(boolean paramBoolean) {
        this.a = paramBoolean;
    }

    public World getWorld() {
        return this.world;
    }

    public double A() {
        return this.locX;
    }

    public double B() {
        return this.locY + 0.5D;
    }

    public double C() {
        return this.locZ;
    }

    public void t_() {
        super.t_();
        if (!this.world.isClientSide && isAlive() && y()) {
            BlockPosition blockPosition = new BlockPosition(this);
            if (blockPosition.equals(this.c)) {
                this.b--;
            } else {
                m(0);
            }
            if (!E()) {
                m(0);
                if (D()) {
                    m(4);
                    update();
                }
            }
        }
    }

    public boolean D() {
        if (TileEntityHopper.a(this))
            return true;
        List<Entity> list = this.world.a(EntityItem.class, getBoundingBox().grow(0.25D, 0.0D, 0.25D), IEntitySelector.a);
        if (list.size() > 0)
            TileEntityHopper.a(this, (EntityItem)list.get(0));
        return false;
    }

    public void a(DamageSource paramDamageSource) {
        super.a(paramDamageSource);
        if (this.world.getGameRules().getBoolean("doEntityDrops"))
            a(Item.getItemOf(Blocks.HOPPER), 1, 0.0F);
    }

    protected void b(NBTTagCompound paramNBTTagCompound) {
        super.b(paramNBTTagCompound);
        paramNBTTagCompound.setInt("TransferCooldown", this.b);
    }

    protected void a(NBTTagCompound paramNBTTagCompound) {
        super.a(paramNBTTagCompound);
        this.b = paramNBTTagCompound.getInt("TransferCooldown");
    }

    public void m(int paramInt) {
        this.b = paramInt;
    }

    public boolean E() {
        return (this.b > 0);
    }

    public String getContainerName() {
        return "minecraft:hopper";
    }

    public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman) {
        return new ContainerHopper(paramPlayerInventory, this, paramEntityHuman);
    }
}