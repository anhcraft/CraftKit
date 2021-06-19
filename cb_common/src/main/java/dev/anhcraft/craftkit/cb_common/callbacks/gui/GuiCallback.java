package dev.anhcraft.craftkit.cb_common.callbacks.gui;

import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.common.callbacks.Callback;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a callback of {@link BaseGUI} events.
 */
public interface GuiCallback extends Callback {
    /**
     * A built-in callback that resets an inventory when it is opened.
     */
    GuiCallback RESET_ON_OPEN = new GuiCallback() {
        private ItemStack[] contents;

        @Override
        public void open(InventoryOpenEvent event, Player player, BaseGUI gui) {
            if(contents == null) contents = ArrayUtils.clone(gui.getContents());
            else gui.setContents(contents);
        }
    };

    /**
     * A built-in callback that resets an inventory when it is closed.
     */
    GuiCallback RESET_ON_CLOSE = new GuiCallback() {
        private ItemStack[] contents;

        @Override
        public void open(InventoryOpenEvent event, Player player, BaseGUI gui) {
            contents = ArrayUtils.clone(gui.getContents());
        }

        @Override
        public void close(InventoryCloseEvent event, Player player, BaseGUI gui) {
            gui.setContents(contents);
        }
    };

    /**
     * This method will be called when {@link InventoryOpenEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param gui the gui
     */
    default void open(InventoryOpenEvent event, Player player, BaseGUI gui) {}

    /**
     * This method will be called when {@link InventoryCloseEvent} is triggered.
     * @param event the event
     * @param player the player
     * @param gui the gui
     */
    default void close(InventoryCloseEvent event, Player player, BaseGUI gui) {}
}
