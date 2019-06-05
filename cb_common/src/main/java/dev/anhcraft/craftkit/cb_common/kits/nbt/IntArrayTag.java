package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

import java.io.Serializable;

/**
 * Represents an integer array NBT tag.
 */
public class IntArrayTag extends NBTTag<int[]> implements Serializable {
    private static final long serialVersionUID = 8440164097225408810L;

    /**
     * Constructs an instance of {@code IntArrayTag} with the given value.
     * @param value the value
     */
    public IntArrayTag(@NotNull int[] value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return 11;
    }
}
