package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInSetCreativeSlot implements Packet<PacketListenerPlayIn> {
    private int slot;

    private ItemStack b;

    //private boolean invalid = false;

    public void a(PacketListenerPlayIn var1) {
        var1.a(this);
    }

    public void a(PacketDataSerializer var1) throws IOException {
        this.slot = var1.readShort();
        this.b = var1.i();
        //this.invalid = var1.isInvalid();
    }

    public void b(PacketDataSerializer var1) throws IOException {
        var1.writeShort(this.slot);
        var1.a(this.b);
    }

    /*public boolean isInvalid() {
        return this.invalid;
    }*/

    public int a() {
        return this.slot;
    }

    public ItemStack getItemStack() {
        return this.b;
    }
}

