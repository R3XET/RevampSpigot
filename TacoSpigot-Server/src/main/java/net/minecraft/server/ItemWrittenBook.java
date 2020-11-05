package net.minecraft.server;

public class ItemWrittenBook extends Item {
    public ItemWrittenBook() {
        c(1);
    }

    public static boolean b(NBTTagCompound var0) {
        if (!ItemBookAndQuill.b(var0))
            return false;
        if (!var0.hasKeyOfType("title", 8))
            return false;
        String var1 = var0.getString("title");
        if (var1 != null && var1.length() <= 32)
            return var0.hasKeyOfType("author", 8);
        return false;
    }
/*
    public static boolean isValid(NBTTagCompound var0) {
        if (!ItemBookAndQuill.isValid(var0))
            return false;
        if (var0.hasKeyOfType("title", 8)) {
            String var1 = var0.getString("title");
            if (var1 != null && var1.length() > Settings.IMP.SETTINGS.BOOK.MAX_TITLE_LENGTH)
                return false;
        }
        if (var0.hasKeyOfType("author", 8)) {
            String var1 = var0.getString("author");
            if (var1 != null && var1.length() > Settings.IMP.SETTINGS.BOOK.MAX_AUTHOR_LENGTH)
                return false;
        }
        return true;
    }*/

    public static int h(ItemStack var0) {
        return var0.getTag().getInt("generation");
    }

    public String a(ItemStack var1) {
        if (var1.hasTag()) {
            NBTTagCompound var2 = var1.getTag();
            String var3 = var2.getString("title");
            if (!UtilColor.b(var3))
                return var3;
        }
        return super.a(var1);
    }

    public ItemStack a(ItemStack var1, World var2, EntityHuman var3) {
        if (!var2.isClientSide)
            a(var1, var3);
        var3.openBook(var1);
        var3.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
        return var1;
    }

    private void a(ItemStack var1, EntityHuman var2) {
        if (var1 != null && var1.getTag() != null) {
            NBTTagCompound var3 = var1.getTag();
            if (!var3.getBoolean("resolved")) {
                var3.setBoolean("resolved", true);
                if (b(var3)) {
                    NBTTagList var4 = var3.getList("pages", 8);
                    for (int var5 = 0; var5 < var4.size(); var5++) {
                        IChatBaseComponent var7;
                        String var6 = var4.getString(var5);
                        try {
                            IChatBaseComponent var11 = IChatBaseComponent.ChatSerializer.a(var6);
                            var7 = ChatComponentUtils.filterForDisplay(var2, var11, var2);
                        } catch (Exception var9) {
                            var7 = new ChatComponentText(var6);
                        }
                        var4.a(var5, new NBTTagString(IChatBaseComponent.ChatSerializer.a(var7)));
                    }
                    var3.set("pages", var4);
                    if (var2 instanceof EntityPlayer && var2.bZ() == var1) {
                        Slot var10 = var2.activeContainer.getSlot(var2.inventory, var2.inventory.itemInHandIndex);
                        ((EntityPlayer)var2).playerConnection
                                .sendPacket(new PacketPlayOutSetSlot(0, var10.rawSlotIndex, var1));
                    }
                }
            }
        }
    }
}

