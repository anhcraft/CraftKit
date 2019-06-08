package dev.anhcraft.craftkit.cb_common.kits.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Represents a {@link Float} NBT tag.
 */
public class FloatTag extends NBTTag<Float> implements Serializable {
    private static final long serialVersionUID = 4247906570762932811L;

    /**
     * Constructs an instance of {@code FloatTag} with the given value.
     * @param value the value
     */
    public FloatTag(@NotNull Float value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return TagType.FLOAT_TAG;
    }
}
