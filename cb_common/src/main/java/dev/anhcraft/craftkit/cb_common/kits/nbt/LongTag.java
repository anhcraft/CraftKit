package dev.anhcraft.craftkit.cb_common.kits.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Represents a {@link Long} NBT tag.
 */
public class LongTag extends NBTTag<Long> implements Serializable {
    private static final long serialVersionUID = -2321407750252801635L;

    /**
     * Constructs an instance of {@code LongTag} with the given value.
     * @param value the value
     */
    public LongTag(@NotNull Long value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return TagType.LONG_TAG;
    }
}
