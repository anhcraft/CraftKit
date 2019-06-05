package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

import java.io.Serializable;

/**
 * Represents a {@link Short} NBT tag.
 */
public class ShortTag extends NBTTag<Short> implements Serializable {
    private static final long serialVersionUID = 4217685668221695611L;

    /**
     * Constructs an instance of {@code ShortTag} with the given value.
     * @param value the value
     */
    public ShortTag(@NotNull Short value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return 2;
    }
}
