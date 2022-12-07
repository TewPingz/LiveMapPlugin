package me.tewpingz.lmp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;

public class LMPListener implements Listener {
    @EventHandler
    public void onMapInitialize(MapInitializeEvent event) {
        event.getMap().removeRenderer(event.getMap().getRenderers().get(0));
        event.getMap().addRenderer(new LMPRenderer());
    }
}
