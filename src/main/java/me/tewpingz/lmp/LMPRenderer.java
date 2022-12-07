package me.tewpingz.lmp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LMPRenderer extends MapRenderer {

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        executor.execute(() -> {
            Location playerLoc = player.getLocation();
            map.setCenterX(playerLoc.getBlockX());
            map.setCenterZ(playerLoc.getBlockZ());
            map.setWorld(player.getWorld());

            for (int relX = 0; relX < 128; ++relX) {
                int x = -64 + relX;
                for (int relY = 0; relY < 128; ++relY) {
                    int z = -64 + relY;
                    Location relLoc = playerLoc.clone().add(x, 0, z);
                    canvas.setPixel(relX, relY, LMP.getInstance().getChunkManager().getWorldChunkManager(player.getWorld()).getColor(relLoc.getBlockX(), relLoc.getBlockZ()));
                }
            }

            this.drawCenteredText(canvas, 64, 10, Color.ORANGE, "Hello World");

            // Cursors
            float yaw = 180 + playerLoc.getYaw();
            byte dir = (byte) ((8 + (15 * (yaw / 360D))) % 15);
            MapCursorCollection collection = new MapCursorCollection();
            collection.addCursor(0, 0, dir);
            canvas.setCursors(collection);
        });
    }

    public void drawCenteredText(MapCanvas canvas, int x, int y, Color color, String text) {
        MinecraftFont font = MinecraftFont.Font;
        x -= font.getWidth(text) / 2;
        y -= font.getHeight() / 2;

        int xStart = x;

        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                x = xStart;
                y += font.getHeight() + 1;
                continue;
            }

            MapFont.CharacterSprite sprite = font.getChar(text.charAt(i));
            for (int r = 0; r < font.getHeight(); ++r) {
                for (int c = 0; c < sprite.getWidth(); ++c) {
                    if (sprite.get(r, c)) {
                        canvas.setPixelColor(x + c, y + r, color);
                    }
                }
            }
            x += sprite.getWidth() + 1;
        }
    }
}
