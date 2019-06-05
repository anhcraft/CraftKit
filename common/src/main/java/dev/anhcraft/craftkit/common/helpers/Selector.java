package dev.anhcraft.craftkit.common.helpers;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;

/**
 * An addition which goes with helpers to select the target object.
 * @param <T> the type of the target
 */
public abstract class Selector<T> {
    private T object;

    protected abstract boolean onSelected(@NotNull T target);

    protected void setTarget(T object){
        this.object = object;
    }

    /**
     * Returns the target.
     * @return the target
     */
    public T getTarget(){
        return object;
    }

    /**
     * Selects a new object.
     * @param object the object
     */
    public void select(@NotNull T object){
        Condition.argNotNull("object", object);
        Condition.check(onSelected(object), "Target is invalid!");
        this.object = object;
    }

    /**
     * Unselects the current object.
     */
    public void unselect(){
        this.object = null;
    }

    /**
     * Checks if this selector has selected its target.
     * @return {@code true} if it has. Otherwise is {@code false}.
     */
    public boolean isSelected(){
        return this.object != null;
    }
}
