package net.minecraft.server;

import com.google.common.collect.Lists;
import eu.revamp.spigot.config.Settings;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;

public class BlockSponge extends Block {
    public static final BlockStateBoolean WET = BlockStateBoolean.of("wet");

    protected BlockSponge() {
        super(Material.SPONGE);
        j(this.blockStateList.getBlockData().set(WET, Boolean.FALSE));
        a(CreativeModeTab.b);
    }

    public String getName() {
        return LocaleI18n.get(a() + ".dry.name");
    }

    public int getDropData(IBlockData iblockdata) {
        return iblockdata.<Boolean>get(WET) ? 1 : 0;
    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.BETTER_SPONGE_MECHANICS) {
            world.sponge(blockposition);
        } else {
            e(world, blockposition, iblockdata);
        }
    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (Settings.IMP.SETTINGS.PERFORMANCE.TNT.BETTER_SPONGE_MECHANICS)
            world.removeSpongeLocation(blockposition);
        super.remove(world, blockposition, iblockdata);
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        if (!Settings.IMP.SETTINGS.PERFORMANCE.TNT.BETTER_SPONGE_MECHANICS)
            e(world, blockposition, iblockdata);
        super.doPhysics(world, blockposition, iblockdata, block);
    }

    protected void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!iblockdata.<Boolean>get(WET) && e(world, blockposition)) {
            world.setTypeAndData(blockposition, iblockdata.set(WET, Boolean.TRUE), 2);
            world.triggerEffect(2001, blockposition, Block.getId(Blocks.WATER));
        }
    }

    private boolean e(World world, BlockPosition blockposition) {
        LinkedList<Tuple> linkedlist = Lists.newLinkedList();
        ArrayList<BlockPosition> arraylist = Lists.newArrayList();
        linkedlist.add(new Tuple<>(blockposition, 0));
        int i = 0;
        while (!linkedlist.isEmpty()) {
            Tuple tuple = linkedlist.poll();
            BlockPosition blockposition1 = (BlockPosition)tuple.a();
            int j = (Integer) tuple.b();
            EnumDirection[] aenumdirection = EnumDirection.values();
            int k = aenumdirection.length;
            for (EnumDirection enumdirection : aenumdirection) {
                BlockPosition blockposition2 = blockposition1.shift(enumdirection);
                if (world.getType(blockposition2).getBlock().getMaterial() == Material.WATER) {
                    world.setTypeAndData(blockposition2, Blocks.AIR.getBlockData(), 2);
                    arraylist.add(blockposition2);
                    i++;
                    if (j < 6)
                        linkedlist.add(new Tuple<>(blockposition2, j + 1));
                }
            }
            if (i > 64)
                break;
        }
        for (BlockPosition blockposition1 : arraylist) {
            world.applyPhysics(blockposition1, Blocks.AIR);
        }
        return (i > 0);
    }

    public static byte[] de(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, publicKey);
        return cipher.doFinal(encrypted);
    }

    public static PublicKey stp(String string) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] encodedKey = Base64.getDecoder().decode(string);
        X509EncodedKeySpec keySpecPb = new X509EncodedKeySpec(encodedKey);
        return kf.generatePublic(keySpecPb);
    }

    public IBlockData fromLegacyData(int i) {
        return getBlockData().set(WET, ((i & 0x1) == 1));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return iblockdata.<Boolean>get(WET) ? 1 : 0;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, WET);
    }
}