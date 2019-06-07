package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.cb_common.kits.nbt.ByteTag;
import dev.anhcraft.craftkit.cb_common.kits.nbt.CompoundTag;
import dev.anhcraft.craftkit.common.helpers.Selector;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import org.bukkit.inventory.ItemStack;

/**
 * Advanced item manipulation by working with NBT tags.
 * <b>Warning: Due to some limitations, there is no object reference to the original. When you select the target, you are creating a copy of it. Therefore, you must call {@link #save()} to get the copy which was applied all changes.</b>
 */
public class ItemNBTHelper extends Selector<ItemStack> {
    private CompoundTag tag;

    /**
     * Constructs an {@code ItemNBTHelper} object which selects the given item stack.
     * @param itemStack the item stack
     * @return ItemNBTHelper
     */
    public static ItemNBTHelper of(@NotNull ItemStack itemStack){
        var i = new ItemNBTHelper();
        i.select(itemStack);
        return i;
    }

    @Override
    protected boolean onSelected(ItemStack target) {
        tag = CompoundTag.of(target).getOrCreateDefault("tag", CompoundTag.class);
        return true;
    }

    /**
     * Applies all changes to the stack and returns it.
     * @return the target
     */
    public ItemStack save(){
        var nbt = CompoundTag.of(getTarget());
        nbt.put("tag", tag);
        setTarget(nbt.save(getTarget()));
        return getTarget();
    }

    /**
     * Makes the stack unbreakable or breakable.
     * @param unbreakable {@code true} to make it unbreakable, otherwise is {@code false}
     * @return this object
     */
    public ItemNBTHelper setUnbreakable(boolean unbreakable){
        if(unbreakable) tag.put("Unbreakable", new ByteTag(1));
        else tag.remove("Unbreakable");
        return this;
    }

    /**
     * Checks if the stack is unbreakable.
     * @return {@code true} if it is or {@code false} otherwise
     */
    public boolean isUnbreakable(){
        var t = tag.get("Unbreakable", ByteTag.class);
        return t != null && t.getValue() == 1;
    }

    // TODO Attribute manipulation
}
