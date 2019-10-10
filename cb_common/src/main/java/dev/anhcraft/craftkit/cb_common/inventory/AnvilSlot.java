package dev.anhcraft.craftkit.cb_common.inventory;

/**
 * Kind of anvil slots.
 */
public enum AnvilSlot {
    /**
     * The item on the left side, represents the first input of an anvil.
     */
    INPUT_LEFT(0),
    /**
     * The item at the center, represents the second input of an anvil.
     */
    INPUT_RIGHT(1),
    /**
     * The item on the right side, represents the output of an anvil.
     */
    OUTPUT(2);

    private int id;

    AnvilSlot(int id) {
        this.id = id;
    }

    /**
     * Returns the id of this slot.
     * @return slot's id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the slot from its id.
     * @param id slot's id
     * @return slot
     */
    public static AnvilSlot getById(int id) {
        return values()[id];
    }
}
