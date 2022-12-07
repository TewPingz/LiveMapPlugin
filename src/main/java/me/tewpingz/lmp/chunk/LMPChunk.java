package me.tewpingz.lmp.chunk;

import it.unimi.dsi.fastutil.objects.Object2ByteArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.tewpingz.lmp.workload.Workload;
import net.minecraft.world.level.material.MaterialMapColor;
import org.bukkit.Bukkit;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;

import java.util.UUID;

public class LMPChunk implements Workload {

    private final UUID worldId;
    private final int chunkX, chunkZ;
    private final Object2ByteMap<ColorKey> colorMap;

    public LMPChunk(UUID worldId, int chunkX, int chunkZ) {
        this.worldId = worldId;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.colorMap = new Object2ByteArrayMap<>();
    }

    @Override
    public void compute() {
        World world = Bukkit.getWorld(this.worldId);
        ChunkSnapshot chunkSnapshot = world.getChunkAt(chunkX, chunkZ).getChunkSnapshot();
        for (int x = 0; x < 16; x++) {
            int previousLevel = -1;
            for (int z = 0; z < 16; z++) {
                if (previousLevel == -1) {
                    previousLevel = this.getHighestBlockY(x + (x == 15 ? -1 : 1), z, world, chunkSnapshot);
                }

                int highestBlock = this.getHighestBlockY(x, z, world, chunkSnapshot);
                Material material = chunkSnapshot.getBlockType(x, highestBlock, z);
                int lowestY = previousLevel;

                if (material == Material.WATER) {
                    lowestY = highestBlock;
                    while (lowestY > -60) {
                        Material relMaterial = chunkSnapshot.getBlockType(x, lowestY, z);
                        if (relMaterial.isSolid()) {
                            break;
                        }
                        lowestY--;
                    }
                }

                int height = this.getLevel(material, highestBlock, lowestY);
                this.colorMap.put(ColorKey.of(x, z), this.getColor(material, height));
                previousLevel = highestBlock;
            }
        }
    }

    private int getHighestBlockY(int x, int z, World world, ChunkSnapshot chunkSnapshot) {
        int y = world.hasCeiling() ? 100 : (world.getMaxHeight() - 1);
        while (y > world.getMinHeight()) {
            Material material = chunkSnapshot.getBlockType(x, y, z);
            if (!material.isAir()) {
                // Ignore this material as it doesn't have a color
                if (material != Material.IRON_BARS) {
                    break;
                }
            }
            y--;
        }
        return y;
    }

    private byte getColor(Material material, int level) {
        net.minecraft.world.level.block.Block block = CraftMagicNumbers.getBlock(material);
        MaterialMapColor mmc = block.s();
        return mmc.b(LMPChunkReflection.getEnumValue(level));
    }

    private int getLevel(Material material, int currentLevel, int previousLevel) {
        if (material == Material.WATER){
            int difference = currentLevel - previousLevel;
            if (difference >= 20) {
                return 3;
            } else if (difference >= 8) {
                return 0;
            } else if (difference >= 4) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (currentLevel > previousLevel) {
                if (currentLevel - previousLevel >= 2) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                if (previousLevel - currentLevel >= 2) {
                    return 3;
                } else {
                    return 0;
                }
            }
        }
    }

    public byte getColor(int x, int z) {
        int relX = x & 15;
        int relZ = z & 15;
        return this.colorMap.getOrDefault(ColorKey.of(relX, relZ), (byte)0);
    }

    @Data
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ColorKey {
        private final int x, z;

        public static ColorKey of(int x, int z) {
            return new ColorKey(x, z);
        }
    }
}
