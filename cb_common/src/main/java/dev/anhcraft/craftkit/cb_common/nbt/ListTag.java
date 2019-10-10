package dev.anhcraft.craftkit.cb_common.nbt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@link List} NBT tag.
 */
public class ListTag extends NBTTag<List<NBTTag>> implements Serializable {
    private static final long serialVersionUID = 7638374686071366548L;

    /**
     * Constructs an instance of {@code ListTag}.
     */
    public ListTag() {
        super(new ArrayList<>());
    }

    @Override
    public int getTypeId() {
        return TagType.LIST_TAG;
    }
}
