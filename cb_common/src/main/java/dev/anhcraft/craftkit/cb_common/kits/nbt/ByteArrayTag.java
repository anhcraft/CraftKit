package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

import java.io.Serializable;

/**
 * Represents a byte array NBT tag.
 */
public class ByteArrayTag extends NBTTag<byte[]> implements Serializable {
    private static final long serialVersionUID = 1234339963093397515L;

    /**
     * Constructs an instance of {@code ByteArrayTag} with the given value.
     * @param value the value
     */
    public ByteArrayTag(@NotNull byte[] value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return TagType.BYTE_ARRAY_TAG;
    }
}
