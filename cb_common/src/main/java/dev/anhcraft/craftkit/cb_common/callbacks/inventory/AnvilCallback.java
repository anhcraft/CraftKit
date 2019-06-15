package dev.anhcraft.craftkit.cb_common.callbacks.inventory;

import dev.anhcraft.craftkit.cb_common.managers.AnvilManager;
import dev.anhcraft.craftkit.common.callbacks.Callback;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * Represents a callback of Anvil events.
 */
public interface AnvilCallback extends Callback {
    /**
     * This method will be called when {@link InventoryCloseEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param manager the manager who owns the anvil
     * @param anvil the unique anvil only for the player
     */
    default void close(InventoryCloseEvent event, Player player, AnvilManager manager, Inventory anvil) {}
}
