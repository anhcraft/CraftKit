package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

import java.io.Serializable;

/**
 * Represents a {@link Double} NBT tag.
 */
public class DoubleTag extends NBTTag<Double> implements Serializable {
    private static final long serialVersionUID = -5834889860699148581L;

    /**
     * Constructs an instance of {@code DoubleTag} with the given value.
     * @param value the value
     */
    public DoubleTag(@NotNull Double value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return TagType.DOUBLE_TAG;
    }
}
