package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This event triggers when a player jumps.
 */
public class PlayerJumpEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private boolean onSpot;

    public PlayerJumpEvent(@NotNull Player player, boolean onSpot){
        super(player);
        this.onSpot = onSpot;
    }

    /**
     * Checks if the player jumped on spot.
     * @return {@code true} if he did or {@code false} if not
     */
    public boolean isOnSpot(){
        return this.onSpot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
