package me.tewpingz.lmp.chunk;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LMPChunkKey {

    private final int chunkX, chunkZ;

    public static LMPChunkKey of(int chunkX, int chunkZ) {
        return new LMPChunkKey(chunkX, chunkZ);
    }

    public static LMPChunkKey ofNormal(int locX, int locZ) {
        int chunkX = locX >> 4;
        int chunkZ = locZ >> 4;
        return LMPChunkKey.of(chunkX, chunkZ);
    }
}
