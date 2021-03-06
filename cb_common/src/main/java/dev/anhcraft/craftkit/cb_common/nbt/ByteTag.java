package dev.anhcraft.craftkit.cb_common.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Represents a {@link Byte} NBT tag.
 */
public class ByteTag extends NBTTag<Byte> implements Serializable {
    private static final long serialVersionUID = -648716370141877202L;

    /**
     * Constructs an instance of {@code ByteTag} with the given value.
     * @param value the value
     */
    public ByteTag(@NotNull Byte value) {
        super(value);
    }

    /**
     * Constructs an instance of {@code ByteTag} with the given value.
     * @param value the value as {@link Integer}
     */
    public ByteTag(@NotNull Integer value) {
        super(value.byteValue());
    }

    @Override
    public int getTypeId() {
        return TagType.BYTE_TAG;
    }
}
