package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

import java.io.Serializable;

/**
 * Represents an {@link Integer} NBT tag.
 */
public class IntTag extends NBTTag<Integer> implements Serializable {
    private static final long serialVersionUID = 2444660994249188851L;

    /**
     * Constructs an instance of {@code IntTag} with the given value.
     * @param value the value
     */
    public IntTag(@NotNull Integer value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return TagType.INT_TAG;
    }
}
