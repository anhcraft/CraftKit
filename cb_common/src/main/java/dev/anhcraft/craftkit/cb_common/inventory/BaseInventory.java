package dev.anhcraft.craftkit.cb_common.inventory;

import dev.anhcraft.craftkit.cb_common.callbacks.inventory.InventoryCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the base inventory
 */
public interface BaseInventory {
    /**
     * Adds the given callback to this inventory
     * @param callback the callback
     */
    void addInventoryCallback(@NotNull InventoryCallback callback);

    /**
     * Clears all {@link InventoryCallback}.
     */
    void clearInventoryCallbacks();

    /**
     * Adds the given callback to a slot.
     * @param position the position of the slot
     * @param callback the callback
     */
    void addSlotCallback(int position, @NotNull SlotCallback callback);

    /**
     * Clears all {@link SlotCallback} from a slot.
     * @param position the position of the slot
     */
    void clearSlotCallbacks(int position);
}
