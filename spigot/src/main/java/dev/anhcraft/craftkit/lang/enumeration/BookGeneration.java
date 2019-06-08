package dev.anhcraft.craftkit.lang.enumeration;


/**
 * Types of book generation.
 */
public enum BookGeneration {
    ORIGINAL(0),
    COPY_ORIGINAL(1),
    COPY_COPY(2),
    TATTERED(3);

    private int id;

    BookGeneration(int id) {
        this.id = id;
    }

    /**
     * Returns the id of this generation.
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the generation from its id.
     * @param id the id
     * @return the generation
     */
    public static BookGeneration getById(int id) {
        return values()[id];
    }
}