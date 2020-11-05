package eu.revamp.spigot.optimization.cache;

import net.minecraft.server.Block;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.Blocks;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkSection;
import net.minecraft.server.IBlockData;
import net.minecraft.server.World;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.block.CraftBlock;

public class SmallCachedRegion {
    private final World world;

    private final int clx;

    private final int clz;

    private final int sizeX;

    private final int sizeZ;

    private final Chunk[][] chunks;

    public SmallCachedRegion(World world, int clx, int cgx, int clz, int cgz) {
        Chunk c2, c1, c3, c = world.getChunkAt(clx, clz);
        if (clx == cgx) {
            c2 = c;
            c3 = c1 = (clz == cgz) ? c : world.getChunkAt(clx, cgz);
        } else if (clz == cgz) {
            c1 = c;
            c3 = c2 = world.getChunkAt(cgx, clz);
        } else {
            c1 = world.getChunkAt(clx, cgz);
            c2 = world.getChunkAt(cgx, clz);
            c3 = world.getChunkAt(cgx, cgz);
        }
        this.world = world;
        this.clx = clx;
        this.clz = clz;
        this.sizeX = cgx - clx + 1;
        this.sizeZ = cgz - clz + 1;
        this.chunks = new Chunk[][] { { c, c1 }, { c2, c3 } };
    }

    public SmallCachedRegion(World world, int clx, int cgx, int clz, int cgz, boolean keepLoaded) {
        this(world, clx, cgx, clz, cgz);
        if (keepLoaded) {
            Chunk[][] arrayOfChunk;
            int i;
            byte b;
            for (i = (arrayOfChunk = this.chunks).length, b = 0; b < i; ) {
                Chunk[] chunks = arrayOfChunk[b];
                Chunk[] arrayOfChunk1;
                int j;
                byte b1;
                for (j = (arrayOfChunk1 = chunks).length, b1 = 0; b1 < j; ) {
                    Chunk chunk = arrayOfChunk1[b1];
                    chunk.lastLoaded = System.currentTimeMillis();
                    b1++;
                }
                b++;
            }
        }
    }

    public SmallCachedRegion(World world, int x, int z, int radius) {
        this(world, x - radius >> 4, x + radius >> 4, z - radius >> 4, z + radius >> 4);
    }

    public IBlockData getType(int x, int y, int z) {
        ChunkSection[] sections;
        int cx = (x >> 4) - this.clx;
        int cz = (z >> 4) - this.clz;
        if (cx < 0 || cx >= this.sizeX || cz < 0 || cz >= this.sizeZ) {
            sections = this.world.getChunkAt(x >> 4, z >> 4).getSections();
        } else {
            sections = this.chunks[(x >> 4) - this.clx][(z >> 4) - this.clz].getSections();
        }
        if (y >= 0 && y >> 4 < sections.length) {
            ChunkSection chunksection = sections[y >> 4];
            if (chunksection != null)
                return chunksection.getType(x & 0xF, y & 0xF, z & 0xF);
        }
        return Blocks.AIR.getBlockData();
    }

    public IBlockData getType(BlockPosition position) {
        return getType(position.getX(), position.getY(), position.getZ());
    }

    public Block getBlock(BlockPosition position) {
        return getType(position.getX(), position.getY(), position.getZ()).getBlock();
    }

    public org.bukkit.block.Block getBukkitBlock(int x, int y, int z) {
        int cx = (x >> 4) - this.clx;
        int cz = (z >> 4) - this.clz;
        if (cx < 0 || cx >= this.sizeX || cz < 0 || cz >= this.sizeZ) {
            return new CraftBlock((CraftChunk)this.world.getChunkAt(x >> 4, z >> 4).bukkitChunk, x, y, z);
        }
        return new CraftBlock((CraftChunk)this.chunks[cx][cz].bukkitChunk, x, y, z);
    }
}

