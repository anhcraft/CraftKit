package dev.anhcraft.craftkit.cb_common.kits.inventory;

import dev.anhcraft.craftkit.cb_common.callbacks.inventory.InventoryCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a custom inventory implementation.<br>
 * This type of inventory contains all methods from {@link Inventory}.
 */
public interface CustomInventory extends Listener, Inventory {
    /**
     * Checks if this inventory is destroyed.
     * @return {@code true} if it is or {@code false} otherwise
     */
    boolean isDestroyed();

    /**
     * Destroys this inventory.<br>
     * It will be cleared and closed then.
     */
    void destroy();

    /**
     * Adds the given callback for this inventory
     * @param callback the callback
     */
    void addInventoryCallback(InventoryCallback callback);

    /**
     * Clears all {@link InventoryCallback}.
     */
    void clearInventoryCallback();

    /**
     * Adds the given callback for a slot.
     * @param position the position of the slot
     * @param callback the callback
     */
    void addSlotCallback(int position, SlotCallback callback);

    /**
     * Clears all {@link SlotCallback} from a slot.
     * @param position the position of the slot
     */
    void clearSlotCallback(int position);

    /**
     * Adds a bunch of item stacks and then add the given callback for all slots which contains those items.
     * @param callback the callback
     * @param itemStacks item stacks to be added
     */
    default void addItem(SlotCallback callback, ItemStack... itemStacks){
        addItem(itemStacks).keySet().forEach(i -> addSlotCallback(i, callback));
    }

    /**
     * Puts the given {@code itemStack} into a slot and then add the callback for that slot.
     * @param position the position of the slot
     * @param callback the callback
     * @param itemStack the item stack to be added
     */
    default void setItem(int position, SlotCallback callback, ItemStack itemStack){
        setItem(position, itemStack);
        addSlotCallback(position, callback);
    }

    /**
     * Adds the given callbacks for all slots in a row.
     * @param row the position of the row (vertically)
     * @param callback the callback
     */
    default void addRowCallback(int row, SlotCallback callback){
        row *= 9;
        for(var i = 0; i < 9; i++) addSlotCallback(i+row, callback);
    }

    /**
     * Clears all {@link SlotCallback} from all slots of a row.
     * @param row the position of the row (vertically)
     */
    default void clearRowCallback(int row){
        row *= 9;
        for(var i = 0; i < 9; i++) clearSlotCallback(i+row);
    }

    /**
     * Adds the given callback for all slots in this inventory.
     * @param callback the callback
     */
    default void addContentCallback(SlotCallback callback){
        for(var i = 0; i < getSize(); i++) addSlotCallback(i, callback);
    }

    /**
     * Clears all {@link SlotCallback} from all slots in this inventory.
     */
    default void clearContentCallback(){
        for(var i = 0; i < getSize(); i++) clearSlotCallback(i);
    }
}
