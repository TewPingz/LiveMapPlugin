package me.tewpingz.lmp.chunk;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class LMPChunkListener implements Listener {

    private final LMPChunkManager chunkManager;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplosion(EntityExplodeEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getEntity().getWorld()).ifPresent(chunkManager -> {
            Set<LMPChunkKey> keySet = new HashSet<>();
            event.blockList().forEach(block -> keySet.add(LMPChunkKey.ofNormal(block.getX(), block.getZ())));
            chunkManager.updateChunks(keySet);
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            Set<LMPChunkKey> keySet = new HashSet<>();
            event.blockList().forEach(block -> keySet.add(LMPChunkKey.ofNormal(block.getX(), block.getZ())));
            chunkManager.updateChunks(keySet);
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStructureGrow(StructureGrowEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getWorld()).ifPresent(chunkManager -> {
            Set<LMPChunkKey> keySet = new HashSet<>();
            event.getBlocks().forEach(blockState -> keySet.add(LMPChunkKey.ofNormal(blockState.getX(), blockState.getZ())));
            chunkManager.updateChunks(keySet);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockFertilize(BlockFertilizeEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            Set<LMPChunkKey> keySet = new HashSet<>();
            event.getBlocks().forEach(blockState -> keySet.add(LMPChunkKey.ofNormal(blockState.getX(), blockState.getZ())));
            chunkManager.updateChunks(keySet);
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent event) {
        ItemStack itemStack = event.getItem();
        Material material = itemStack.getType();

        if (!(material == Material.LAVA_BUCKET || material == Material.WATER_BUCKET || material == Material.SHULKER_BOX || material == Material.FLINT_AND_STEEL)) {
            return;
        }

        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            chunkManager.updateChunks(LMPChunkKey.ofNormal(event.getBlock().getX(), event.getBlock().getZ()));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            Set<LMPChunkKey> keySet = new HashSet<>();
            int size = event.getBlocks().size();
            for (int i = 0; i < size; i++) {
                Block block = event.getBlocks().get(i);
                keySet.add(LMPChunkKey.ofNormal(block.getX(), block.getZ()));
                if (i == size - 1) {
                    Block relBlock = block.getRelative(event.getDirection());
                    keySet.add(LMPChunkKey.ofNormal(relBlock.getX(), relBlock.getZ()));
                }
                chunkManager.updateChunks(keySet);
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        this.chunkManager.getWorldChunkManagerIfExists(event.getBlock().getWorld()).ifPresent(chunkManager -> {
            Set<LMPChunkKey> keySet = new HashSet<>();
            int size = event.getBlocks().size();
            for (int i = 0; i < size; i++) {
                Block block = event.getBlocks().get(i);
                keySet.add(LMPChunkKey.ofNormal(block.getX(), block.getZ()));
            }
        });
    }
}
