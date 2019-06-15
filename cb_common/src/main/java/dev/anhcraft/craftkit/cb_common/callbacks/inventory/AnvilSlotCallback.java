package dev.anhcraft.craftkit.cb_common.callbacks.inventory;

import dev.anhcraft.craftkit.cb_common.managers.AnvilManager;
import dev.anhcraft.craftkit.cb_common.lang.enumeration.AnvilSlot;
import dev.anhcraft.craftkit.common.callbacks.Callback;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Represents a callback of {@link AnvilSlot} events.
 */
public interface AnvilSlotCallback extends Callback {
    /**
     * A built-in callback that prevents players from modifying slots.
     */
    AnvilSlotCallback PREVENT_MODIFY = new AnvilSlotCallback() {
        @Override
        public void click(InventoryClickEvent event, Player player, AnvilManager manager, Inventory anvil) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    };

    /**
     * A built-in callback that close the inventory when a player clicks the slot.
     */
    AnvilSlotCallback CLOSE_INVENTORY = new AnvilSlotCallback() {
        @Override
        public void click(InventoryClickEvent event, Player player, AnvilManager manager, Inventory anvil) {
            CKProvider.TASK_HELPER.newTask(player::closeInventory);
        }
    };

    /**
     * This method is called when {@link InventoryClickEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param manager the manager who owns the anvil
     * @param anvil the unique anvil only for the player
     */
    default void click(InventoryClickEvent event, Player player, AnvilManager manager, Inventory anvil) {}
}
