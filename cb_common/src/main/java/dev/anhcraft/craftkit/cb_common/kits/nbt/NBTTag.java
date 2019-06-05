package dev.anhcraft.craftkit.cb_common.kits.nbt;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;

import java.io.Serializable;

/**
 * Represents NBT tags implementation.
 * @param <T> the type of tag values
 */
public abstract class NBTTag<T> implements Serializable {
    private static final long serialVersionUID = 8925614729508122965L;

    private T value;

    /**
     * Creates a default NBT tag.
     * @param typeId the id of tag's type
     * @return the tag
     */
    public static NBTTag createDefaultTag(int typeId){
        switch (typeId){
            case 1: return new ByteTag((byte) 0);
            case 2: return new ShortTag((short) 0);
            case 3: return new IntTag(0);
            case 4: return new LongTag(0L);
            case 5: return new FloatTag(0f);
            case 6: return new DoubleTag(0d);
            case 7: return new ByteArrayTag(new byte[]{});
            case 8: return new StringTag("");
            case 9: return new ListTag();
            case 10: return new CompoundTag();
            case 11: return new IntArrayTag(new int[]{});
            case 12: return new LongArrayTag(new long[]{});
        }
        return null;
    }

    /**
     * Constructs an instance of {@code NBTTag} with the given value.
     * @param value the value
     */
    public NBTTag(@NotNull T value){
        Condition.argNotNull("value", value);
        this.value = value;
    }

    /**
     * Returns the id of this tag type.
     * @return id
     */
    public abstract int getTypeId();

    /**
     * Returns the value of this tag.
     * @return tag's value
     */
    public T getValue(){
        return value;
    }
}
