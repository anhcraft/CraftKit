package dev.anhcraft.craftkit.cb_common.callbacks.inventory;

import dev.anhcraft.craftkit.cb_common.kits.inventory.CustomInventory;
import dev.anhcraft.craftkit.common.callbacks.Callback;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Represents a callback of slot events which happens over {@link CustomInventory}.
 */
public interface SlotCallback extends Callback {
    /**
     * A built-in callback that prevents players from modifying slots.
     */
    SlotCallback PREVENT_MODIFY = new SlotCallback() {
        @Override
        public void click(InventoryClickEvent event, Player player, CustomInventory inventory) {
            event.setCancelled(true);
        }
    };

    /**
     * A built-in callback that close the inventory when a player clicks the slot.
     */
    SlotCallback CLOSE_INVENTORY = new SlotCallback() {
        @Override
        public void click(InventoryClickEvent event, Player player, CustomInventory inventory) {
            CKProvider.TASK_HELPER.newTask(player::closeInventory);
        }
    };

    /**
     * This method is called when {@link InventoryClickEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param inventory the inventory
     */
    default void click(InventoryClickEvent event, Player player, CustomInventory inventory) {}
}
