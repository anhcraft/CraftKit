package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.events.PlayerJumpEvent;
import dev.anhcraft.craftkit.internal.tasks.ArmorHandleTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PlayerListener implements Listener {
    private static final double DELTA_JUMP_HEIGHT = 0.33319999363422426;
    public static final Map<UUID, Location> FREEZED_PLAYERS = new HashMap<>();

    private boolean checkFreeze(Player p, Location to, Cancellable e){
        if(to == null || to.getWorld() == null) return true;
        Location last = FREEZED_PLAYERS.get(p.getUniqueId());
        if(last != null) {
            if(Objects.equals(last.getWorld(), to.getWorld())) {
                double offX = to.getX() - last.getX();
                double offY = to.getY() - last.getY();
                double offZ = to.getZ() - last.getZ();
                if (offX * offX + offY * offY + offZ * offZ >= 1) {
                    e.setCancelled(true);
                    return false;
                }
            } else {
                e.setCancelled(true);
                return false;
            }
        }
        return true;
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        UUID id = e.getPlayer().getUniqueId();
        ArmorHandleTask.data.remove(id);
        FREEZED_PLAYERS.remove(id);
    }

    @EventHandler
    public void teleport(PlayerTeleportEvent e){
        if(!checkFreeze(e.getPlayer(), e.getTo(), e)){
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        checkFreeze(e.getPlayer(), e.getTo(), e);
        if (e.getTo() != null) {
            double a = e.getTo().getY() - e.getFrom().getY();
            if (a == DELTA_JUMP_HEIGHT) {
                PlayerJumpEvent ev = new PlayerJumpEvent(e.getPlayer(),
                        e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ());
                Bukkit.getPluginManager().callEvent(ev);
            }
        }
    }
}
