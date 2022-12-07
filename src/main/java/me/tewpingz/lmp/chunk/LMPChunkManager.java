package me.tewpingz.lmp.chunk;

import me.tewpingz.lmp.LMP;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LMPChunkManager {

    private final Map<UUID, LMPWorldChunkManager> map;

    public LMPChunkManager() {
        this.map = new HashMap<>();
    }

    public Optional<LMPWorldChunkManager> getWorldChunkManagerIfExists(World world) {
        return Optional.ofNullable(this.map.get(world.getUID()));
    }

    public LMPWorldChunkManager getWorldChunkManager(World world) {
        return this.map.computeIfAbsent(world.getUID(), (worldId) -> new LMPWorldChunkManager(worldId, LMP.getInstance()));
    }
}
