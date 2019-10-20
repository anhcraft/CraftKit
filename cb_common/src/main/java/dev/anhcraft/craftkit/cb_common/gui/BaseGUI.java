package dev.anhcraft.craftkit.cb_common.gui;

import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a GUI.
 */
public interface BaseGUI extends Inventory, BaseUI<GuiCallback, SlotCallback> {
    /**
     * Adds a bunch of item stacks and then add the given callback for all slots which contains those items.
     * @param callback the callback
     * @param itemStacks item stacks to be added
     */
    default void addItems(@NotNull SlotCallback callback, @NotNull ItemStack... itemStacks){
        Condition.argNotNull("callback", callback);
        Condition.argNotNull("itemStacks", itemStacks);
        addItem(itemStacks).keySet().forEach(i -> addSlotCallback(i, callback));
    }

    /**
     * Adds an item stacks with callbacks.
     * @param itemStack the item stack to be added
     * @param callbacks the callback
     * @return {@code true} if added successfully
     */
    default boolean addItem(@NotNull ItemStack itemStack, @NotNull SlotCallback... callbacks){
        Condition.argNotNull("itemStack", itemStack);
        Condition.argNotNull("callbacks", callbacks);
        int s = firstEmpty();
        if(s == -1) return false;
        setItem(s, itemStack);
        addSlotCallback(s, callbacks);
        return true;
    }

    /**
     * Puts the given {@code itemStack} into a slot and then add callbacks for that slot.
     * @param position the position of the slot (from 0)
     * @param itemStack the item stack to be added
     * @param callbacks the callbacks
     */
    default void setItem(int position, @NotNull ItemStack itemStack, @NotNull SlotCallback... callbacks){
        Condition.argNotNull("itemStack", itemStack);
        Condition.argNotNull("callbacks", callbacks);
        setItem(position, itemStack);
        addSlotCallback(position, callbacks);
    }

    /**
     * Adds given callbacks for all slots in a row.
     * @param row the position of the row (from 0)
     * @param callbacks the callbacks
     */
    default void addRowCallback(int row, @NotNull SlotCallback... callbacks){
        Condition.argNotNull("callbacks", callbacks);
        row *= 9;
        for(int i = 0; i < 9; i++) addSlotCallback(i+row, callbacks);
    }

    /**
     * Clears all {@link SlotCallback} from all slots of a row.
     * @param row the position of the row (from 0)
     */
    default void clearRowCallbacks(int row){
        row *= 9;
        for(int i = 0; i < 9; i++) clearSlotCallbacks(i+row);
    }

    /**
     * Adds given callbacks for all slots in this inventory.
     * @param callbacks the callbacks
     */
    default void addContentCallback(@NotNull SlotCallback... callbacks){
        Condition.argNotNull("callbacks", callbacks);
        for(int i = 0; i < getSize(); i++) addSlotCallback(i, callbacks);
    }

    /**
     * Clears all {@link SlotCallback} from all slots in this inventory.
     */
    default void clearContentCallbacks(){
        for(int i = 0; i < getSize(); i++) clearSlotCallbacks(i);
    }

    void onClick(@NotNull InventoryClickEvent event);

    void onOpen(@NotNull InventoryOpenEvent event);

    void onClose(@NotNull InventoryCloseEvent event);
}
