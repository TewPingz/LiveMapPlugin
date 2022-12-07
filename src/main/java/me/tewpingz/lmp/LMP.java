package me.tewpingz.lmp;

import lombok.Getter;
import me.tewpingz.lmp.chunk.LMPChunkListener;
import me.tewpingz.lmp.chunk.LMPChunkManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class LMP extends JavaPlugin {

    @Getter
    private static LMP instance;
    private LMPChunkManager chunkManager;

    @Override
    public void onEnable() {
        instance = this;
        this.chunkManager = new LMPChunkManager();
        this.getServer().getPluginManager().registerEvents(new LMPChunkListener(this.chunkManager), this);
        this.getServer().getPluginManager().registerEvents(new LMPListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
