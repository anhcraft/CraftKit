package dev.anhcraft.craftkit.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.DataInputStream;

/**
 * This event is called when the data is forwarded from a plugin though the Bungeecord messaging channel.
 */
public class BungeeForwardEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private DataInputStream data;

    public BungeeForwardEvent(DataInputStream data){
        this.data = data;
    }

    /**
     * Returns the data.
     * @return data
     */
    public DataInputStream getData(){
        return this.data;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}