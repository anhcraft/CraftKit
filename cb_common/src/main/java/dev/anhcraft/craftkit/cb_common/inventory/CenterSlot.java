package dev.anhcraft.craftkit.cb_common.inventory;

/**
 * Contains center slot.
 */
public enum CenterSlot {
    /**
     * The single center position.
     */
    CENTER_1(4),

    /**
     * The center position of first stuff in two ones.
     */
    CENTER_2_A(2),
    /**
     * The center position of second stuff in two ones.
     */
    CENTER_2_B(6),

    /**
     * The center position of first stuff in three ones.
     */
    CENTER_3_A(1),
    /**
     * The center position of second stuff in three ones.
     */
    CENTER_3_B(4),
    /**
     * The center position of third stuff in three ones.
     */
    CENTER_3_C(7),

    /**
     * The center position of first stuff in four ones.
     */
    CENTER_4_A(1),
    /**
     * The center position of second stuff in four ones.
     */
    CENTER_4_B(3),
    /**
     * The center position of three stuff in four ones.
     */
    CENTER_4_C(5),
    /**
     * The center position of forth stuff in four ones.
     */
    CENTER_4_D(7),

    /**
     * The center position of first stuff in five ones.
     */
    CENTER_5_A(0),
    /**
     * The center position of second stuff in five ones.
     */
    CENTER_5_B(2),
    /**
     * The center position of third stuff in five ones.
     */
    CENTER_5_C(4),
    /**
     * The center position of forth stuff in five ones.
     */
    CENTER_5_D(6),
    /**
     * The center position of fifth stuff in five ones.
     */
    CENTER_5_E(8);

    private int slot;

    CenterSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public int row(int rowIndex){
        return rowIndex * 9 + slot;
    }
}
