package dev.anhcraft.craftkit.events;

import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;

/**
 * This event triggers when the server is going to stop.
 */
public class ServerStopEvent extends ServerEvent {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
