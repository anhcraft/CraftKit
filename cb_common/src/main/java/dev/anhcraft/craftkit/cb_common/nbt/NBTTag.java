package dev.anhcraft.craftkit.cb_common.nbt;

import org.jetbrains.annotations.NotNull;
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
     * @param typeId the tag's type
     * @return the tag
     */
    @NotNull
    public static NBTTag createDefaultTag(int typeId){
        switch (typeId){
            case TagType.BYTE_TAG: return new ByteTag((byte) 0);
            case TagType.SHORT_TAG: return new ShortTag((short) 0);
            case TagType.INT_TAG: return new IntTag(0);
            case TagType.LONG_TAG: return new LongTag(0L);
            case TagType.FLOAT_TAG: return new FloatTag(0f);
            case TagType.DOUBLE_TAG: return new DoubleTag(0d);
            case TagType.BYTE_ARRAY_TAG: return new ByteArrayTag(new byte[]{});
            case TagType.STRING_TAG: return new StringTag("");
            case TagType.LIST_TAG: return new ListTag();
            case TagType.COMPOUND_TAG: return new CompoundTag();
            case TagType.INT_ARRAY_TAG: return new IntArrayTag(new int[]{});
            case TagType.LONG_ARRAY_TAG: return new LongArrayTag(new long[]{});
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a default NBT tag.
     * @param classType the tag's class type
     * @param <E> type of the tag
     * @return the tag
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public static <E extends NBTTag> E createDefaultTag(@NotNull Class<E> classType){
        Condition.argNotNull("classType", classType);
        if (ByteTag.class.isAssignableFrom(classType)) {
            return (E) new ByteTag((byte) 0);
        } else if (ShortTag.class.isAssignableFrom(classType)) {
            return (E) new ShortTag((short) 0);
        } else if (IntTag.class.isAssignableFrom(classType)) {
            return (E) new IntTag(0);
        } else if (LongTag.class.isAssignableFrom(classType)) {
            return (E) new LongTag(0L);
        } else if (FloatTag.class.isAssignableFrom(classType)) {
            return (E) new FloatTag(0f);
        } else if (DoubleTag.class.isAssignableFrom(classType)) {
            return (E) new DoubleTag(0d);
        } else if (ByteArrayTag.class.isAssignableFrom(classType)) {
            return (E) new ByteArrayTag(new byte[]{});
        } else if (StringTag.class.isAssignableFrom(classType)) {
            return (E) new StringTag("");
        } else if (ListTag.class.isAssignableFrom(classType)) {
            return (E) new ListTag();
        } else if (CompoundTag.class.isAssignableFrom(classType)) {
            return (E) new CompoundTag();
        } else if (IntArrayTag.class.isAssignableFrom(classType)) {
            return (E) new IntArrayTag(new int[]{});
        } else if (LongArrayTag.class.isAssignableFrom(classType)) {
            return (E) new LongArrayTag(new long[]{});
        }
        throw new UnsupportedOperationException();
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
    @NotNull
    public T getValue(){
        return value;
    }

    /**
     * Overrides the value of this tag.
     * @param value new tag
     */
    public void setValue(@NotNull T value) {
        Condition.argNotNull("value", value);
        this.value = value;
    }
}
