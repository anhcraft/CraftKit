package dev.anhcraft.craftkit.cb_common.nbt;

import org.jetbrains.annotations.NotNull;

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

    /**
     * Constructs an instance of {@code ByteTag} with the given value.
     * @param value the value as {@link Integer}
     */
    public ShortTag(@NotNull Integer value) {
        super(value.shortValue());
    }

    @Override
    public int getTypeId() {
        return TagType.SHORT_TAG;
    }
}
