package dev.anhcraft.craftkit.cb_common.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Represents a {@link String} NBT tag.
 */
public class StringTag extends NBTTag<String> implements Serializable {
    private static final long serialVersionUID = 6219513427408036899L;

    /**
     * Constructs an instance of {@code StringTag} with the given value.
     * @param value the value
     */
    public StringTag(@NotNull String value) {
        super(value);
    }

    @Override
    public int getTypeId() {
        return TagType.STRING_TAG;
    }
}
