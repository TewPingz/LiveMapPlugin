package me.tewpingz.lmp.chunk;

import com.google.common.cache.CacheLoader;
import lombok.RequiredArgsConstructor;
import me.tewpingz.lmp.workload.WorkloadRunnable;

import java.util.UUID;

@RequiredArgsConstructor
public class LMPChunkCacheLoader extends CacheLoader<LMPChunkKey, LMPChunk> {

    private final WorkloadRunnable workloadRunnable;
    private final UUID worldId;

    @Override
    public LMPChunk load(LMPChunkKey key) throws Exception {
        LMPChunk chunk = new LMPChunk(this.worldId, key.getChunkX(), key.getChunkZ());
        this.workloadRunnable.addWorkload(chunk);
        return chunk;
    }
}
