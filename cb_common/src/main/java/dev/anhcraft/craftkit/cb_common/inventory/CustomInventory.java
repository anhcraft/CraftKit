package dev.anhcraft.craftkit.cb_common.inventory;

import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom inventory.
 */
public interface CustomInventory extends Inventory, BaseInventory {
    /**
     * Adds a bunch of item stacks and then add the given callback for all slots which contains those items.
     * @param callback the callback
     * @param itemStacks item stacks to be added
     */
    default void addItem(@NotNull SlotCallback callback, @NotNull ItemStack... itemStacks){
        Condition.argNotNull("callback", callback);
        Condition.argNotNull("itemStacks", itemStacks);
        addItem(itemStacks).keySet().forEach(i -> addSlotCallback(i, callback));
    }

    /**
     * Puts the given {@code itemStack} into a slot and then add the callback for that slot.
     * @param position the position of the slot
     * @param callback the callback
     * @param itemStack the item stack to be added
     */
    default void setItem(int position, @NotNull SlotCallback callback, @NotNull ItemStack itemStack){
        Condition.argNotNull("callback", callback);
        Condition.argNotNull("itemStack", itemStack);
        setItem(position, itemStack);
        addSlotCallback(position, callback);
    }

    /**
     * Adds the given callbacks for all slots in a row.
     * @param row the position of the row (vertically)
     * @param callback the callback
     */
    default void addRowCallback(int row, @NotNull SlotCallback callback){
        Condition.argNotNull("callback", callback);
        row *= 9;
        for(int i = 0; i < 9; i++) addSlotCallback(i+row, callback);
    }

    /**
     * Clears all {@link SlotCallback} from all slots of a row.
     * @param row the position of the row (vertically)
     */
    default void clearRowCallback(int row){
        row *= 9;
        for(int i = 0; i < 9; i++) clearSlotCallbacks(i+row);
    }

    /**
     * Adds the given callback for all slots in this inventory.
     * @param callback the callback
     */
    default void addContentCallback(@NotNull SlotCallback callback){
        Condition.argNotNull("callback", callback);
        for(int i = 0; i < getSize(); i++) addSlotCallback(i, callback);
    }

    /**
     * Clears all {@link SlotCallback} from all slots in this inventory.
     */
    default void clearContentCallback(){
        for(int i = 0; i < getSize(); i++) clearSlotCallbacks(i);
    }

    void onClick(@NotNull InventoryClickEvent event);

    void onOpen(@NotNull InventoryOpenEvent event);

    void onClose(@NotNull InventoryCloseEvent event);
}
