package dev.anhcraft.craftkit.cb_common.nbt;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;

import java.io.Serializable;

/**
 * Represents a long array NBT tag.<br>
 * Only supports this tag on 1.12+.
 */
public class LongArrayTag extends NBTTag<long[]> implements Serializable {
    private static final long serialVersionUID = 5188750540224844814L;

    /**
     * Constructs an instance of {@code LongArrayTag} with the given value.
     * @param value the value
     */
    public LongArrayTag(@NotNull long[] value) {
        super(value);
        Condition.check(NMSVersion.current().compare(NMSVersion.v1_12_R1) < 0,
                "long[] NBT tag is not supported");
    }

    @Override
    public int getTypeId() {
        return TagType.LONG_ARRAY_TAG;
    }
}
