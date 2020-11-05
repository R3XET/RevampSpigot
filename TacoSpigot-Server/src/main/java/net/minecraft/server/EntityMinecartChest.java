package net.minecraft.server;

public class EntityMinecartChest extends EntityMinecartContainer {
    public EntityMinecartChest(World paramWorld) {
        super(paramWorld);
    }

    public EntityMinecartChest(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
        super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
    }

    public void a(DamageSource paramDamageSource) {
        super.a(paramDamageSource);
        if (this.world.getGameRules().getBoolean("doEntityDrops"))
            a(Item.getItemOf(Blocks.CHEST), 1, 0.0F);
    }

    public int getSize() {
        return 27;
    }

    public EntityMinecartAbstract.EnumMinecartType s() {
        return EntityMinecartAbstract.EnumMinecartType.CHEST;
    }

    public IBlockData u() {
        return Blocks.CHEST.getBlockData().set(BlockChest.FACING, EnumDirection.NORTH);
    }

    public int w() {
        return 8;
    }

    public String getContainerName() {
        return "minecraft:chest";
    }

    public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman) {
        return new ContainerChest(paramPlayerInventory, this, paramEntityHuman);
    }
}

