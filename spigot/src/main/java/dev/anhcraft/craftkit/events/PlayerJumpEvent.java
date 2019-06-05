package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event triggers when a player jumps.
 */
public class PlayerJumpEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private boolean onSpot;

    public PlayerJumpEvent(Player player, boolean onSpot){
        this.player = player;
        this.onSpot = onSpot;
    }

    /**
     * Returns the player who jumped.
     * @return the player
     */
    public Player getPlayer(){
        return this.player;
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
