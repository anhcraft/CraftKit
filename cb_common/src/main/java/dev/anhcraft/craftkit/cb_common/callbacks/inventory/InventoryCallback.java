package dev.anhcraft.craftkit.cb_common.callbacks.inventory;

import dev.anhcraft.craftkit.cb_common.kits.inventory.CustomInventory;
import dev.anhcraft.craftkit.common.callbacks.Callback;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a callback of {@link CustomInventory} events.
 */
public interface InventoryCallback extends Callback {
    /**
     * A built-in callback that resets an inventory when it is opened.
     */
    InventoryCallback RESET_ON_OPEN = new InventoryCallback() {
        private ItemStack[] contents;

        @Override
        public void open(InventoryOpenEvent event, Player player, CustomInventory inventory) {
            if(contents == null) contents = (ItemStack[]) ArrayUtils.clone(inventory.getContents());
            else inventory.setContents(contents);
        }
    };

    /**
     * A built-in callback that resets an inventory when it is closed.
     */
    InventoryCallback RESET_ON_CLOSE = new InventoryCallback() {
        private ItemStack[] contents;

        @Override
        public void open(InventoryOpenEvent event, Player player, CustomInventory inventory) {
            contents = (ItemStack[]) ArrayUtils.clone(inventory.getContents());
        }

        @Override
        public void close(InventoryCloseEvent event, Player player, CustomInventory inventory) {
            inventory.setContents(contents);
        }
    };

    /**
     * A built-in callback that destroys an inventory when it is closed.
     */
    InventoryCallback DESTROY_ON_CLOSE = new InventoryCallback() {
        @Override
        public void close(InventoryCloseEvent event, Player player, CustomInventory inventory) {
            inventory.destroy();
        }
    };

    /**
     * This method will be called when {@link InventoryOpenEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param inventory the inventory
     */
    default void open(InventoryOpenEvent event, Player player, CustomInventory inventory) {}

    /**
     * This method will be called when {@link InventoryCloseEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param inventory the inventory
     */
    default void close(InventoryCloseEvent event, Player player, CustomInventory inventory) {}
}
