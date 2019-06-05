package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.internal.assistants.CKCleaner;
import dev.anhcraft.craftkit.common.lang.annotation.RequiredCleaner;
import dev.anhcraft.craftkit.events.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {
    private static final double DELTA_JUMP_HEIGHT = 0.33319999363422426; // this is the common jump height of players (we can use this to verify a movement and make sure it is "jump", not "fall down" or "go down")
    @RequiredCleaner
    public static final Map<UUID, Location> freezedPlayers = new HashMap<>();

    @EventHandler
    public void quit(PlayerQuitEvent event){
        CKProvider.TASK_HELPER.newAsyncTask(() -> CKCleaner.clean(o -> o.equals(event.getPlayer()) || o.equals(event.getPlayer().getUniqueId())));
    }

    @EventHandler
    public void teleport(PlayerTeleportEvent e){
        Location last = freezedPlayers.get(e.getPlayer().getUniqueId());
        if(last != null) {
            double offX = e.getTo().getX() - last.getX();
            double offY = e.getTo().getY() - last.getY();
            double offZ = e.getTo().getZ() - last.getZ();
            if (offX * offX + offY * offY + offZ * offZ >= 1) e.setCancelled(true);
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        Location last = freezedPlayers.get(e.getPlayer().getUniqueId());
        if(last != null) {
            double offX = e.getTo().getX() - last.getX();
            double offY = e.getTo().getY() - last.getY();
            double offZ = e.getTo().getZ() - last.getZ();
            if (offX * offX + offY * offY + offZ * offZ >= 1) e.setCancelled(true);
        }
        double a = e.getTo().getY() - e.getFrom().getY();
        if(a == DELTA_JUMP_HEIGHT) {
            PlayerJumpEvent ev = new PlayerJumpEvent(e.getPlayer(),
                    e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ());
            Bukkit.getPluginManager().callEvent(ev);
        }
    }
}
