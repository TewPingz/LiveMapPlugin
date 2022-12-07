package me.tewpingz.lmp.chunk;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import me.tewpingz.lmp.LMP;
import me.tewpingz.lmp.workload.WorkloadRunnable;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LMPWorldChunkManager {

    private final WorkloadRunnable workloadRunnable;
    private final LoadingCache<LMPChunkKey, LMPChunk> chunkMap;

    public LMPWorldChunkManager(UUID worldId, LMP instance) {
        this.workloadRunnable = new WorkloadRunnable();
        this.chunkMap = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build(new LMPChunkCacheLoader(workloadRunnable, worldId));
        Bukkit.getScheduler().runTaskTimer(instance, workloadRunnable, 1, 1);
    }

    public void updateChunks(LMPChunkKey... keys) {
        for (LMPChunkKey chunkKey : keys) {
            LMPChunk chunk = this.chunkMap.getIfPresent(chunkKey);
            if (chunk == null) {
                continue;
            }
            this.workloadRunnable.addWorkload(chunk);
        }
    }

    public void updateChunks(Collection<LMPChunkKey> keys) {
        for (LMPChunkKey chunkKey : keys) {
            LMPChunk chunk = this.chunkMap.getIfPresent(chunkKey);
            if (chunk == null) {
                continue;
            }
            this.workloadRunnable.addWorkload(chunk);
        }
    }

    public byte getColor(int locX, int locZ) {
        try {
            return this.chunkMap.get(LMPChunkKey.ofNormal(locX, locZ)).getColor(locX, locZ);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
