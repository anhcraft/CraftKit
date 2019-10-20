package dev.anhcraft.craftkit.cb_common.gui;

import dev.anhcraft.craftkit.common.callbacks.Callback;
import dev.anhcraft.jvmkit.utils.Condition;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an user interface.
 */
public interface BaseUI<I extends Callback, S extends Callback> {
    /**
     * Adds the given callback to this UI.
     * @param callback the callback
     */
    void addInterfaceCallback(@NotNull I callback);

    /**
     * Adds multiple callbacks to a this UI.
     * @param callbacks the callbacks
     */
    default void addInterfaceCallback(@NotNull I... callbacks){
        Condition.argNotNull("callbacks", callbacks);
        for(I callback : callbacks){
            addInterfaceCallback(callback);
        }
    }

    /**
     * Adds multiple callbacks to a this UI.
     * @param callbacks the callbacks
     */
    default void addInterfaceCallback(@NotNull Iterable<I> callbacks){
        Condition.argNotNull("callbacks", callbacks);
        for(I callback : callbacks){
            addInterfaceCallback(callback);
        }
    }

    /**
     * Clears all UI callbacks.
     */
    void clearInterfaceCallbacks();

    /**
     * Adds the given callback to a slot.
     * @param position the position of the slot
     * @param callback the callback
     */
    void addSlotCallback(int position, @NotNull S callback);

    /**
     * Adds multiple callbacks to a slot.
     * @param position the position of the slot
     * @param callbacks the callbacks
     */
    default void addSlotCallback(int position, @NotNull S... callbacks){
        Condition.argNotNull("callbacks", callbacks);
        for(S callback : callbacks){
            addSlotCallback(position, callback);
        }
    }

    /**
     * Adds multiple callbacks to a slot.
     * @param position the position of the slot
     * @param callbacks the callbacks
     */
    default void addSlotCallback(int position, @NotNull Iterable<S> callbacks){
        Condition.argNotNull("callbacks", callbacks);
        for(S callback : callbacks){
            addSlotCallback(position, callback);
        }
    }

    /**
     * Clears all slot callbacks.
     * @param position the position of the slot
     */
    void clearSlotCallbacks(int position);
}
