package dev.anhcraft.craftkit.utils;

import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Extra methods for working with the inventory.
 */
public class InventoryUtil {
    /**
     * The single center position.
     */
    public static final int CENTER_POS = 4;

    /**
     * The center position of first stuff in two ones.
     */
    public static final int CENTER_POS_2_A = 2;
    /**
     * The center position of second stuff in two ones.
     */
    public static final int CENTER_POS_2_B = 6;

    /**
     * The center position of first stuff in three ones.
     */
    public static final int CENTER_POS_3_A = 1;
    /**
     * The center position of second stuff in three ones.
     */
    public static final int CENTER_POS_3_B = 4;
    /**
     * The center position of third stuff in three ones.
     */
    public static final int CENTER_POS_3_C = 7;

    /**
     * The center position of first stuff in four ones.
     */
    public static final int CENTER_POS_4_A = 1;
    /**
     * The center position of second stuff in four ones.
     */
    public static final int CENTER_POS_4_B = 3;
    /**
     * The center position of three stuff in four ones.
     */
    public static final int CENTER_POS_4_C = 5;
    /**
     * The center position of forth stuff in four ones.
     */
    public static final int CENTER_POS_4_D = 7;

    /**
     * The center position of first stuff in five ones.
     */
    public static final int CENTER_POS_5_A = 0;
    /**
     * The center position of second stuff in five ones.
     */
    public static final int CENTER_POS_5_B = 2;
    /**
     * The center position of third stuff in five ones.
     */
    public static final int CENTER_POS_5_C = 4;
    /**
     * The center position of forth stuff in five ones.
     */
    public static final int CENTER_POS_5_D = 6;
    /**
     * The center position of fifth stuff in five ones.
     */
    public static final int CENTER_POS_5_E = 8;

    /**
     * Stores the given item stack in {@code inventory} only if that item is unique.
     * @param inventory the inventory
     * @param itemStack the item stack
     * @return {@code true} if that item was added (which means it was unique) or {@code false} if there was another similar item
     */
    public static boolean addUniqueItem(@NotNull Inventory inventory, @Nullable ItemStack itemStack){
        Condition.argNotNull("inventory", inventory);
        if(Arrays.stream(inventory.getContents()).noneMatch(x -> ItemUtil.compare(x, itemStack))){
            inventory.addItem(itemStack);
            return true;
        }
        return false;
    }

    /**
     * Fills all slots in {@code inventory} with the given item stack.
     * @param inventory the inventory
     * @param itemStack the item stack
     */
    public static void fillAll(@NotNull Inventory inventory, @Nullable ItemStack itemStack){
        Condition.argNotNull("inventory", inventory);
        for(var i = 0; i < inventory.getSize(); i++) inventory.setItem(i, itemStack);
    }

    /**
     * Fills all empty slots in {@code inventory} with the given item stack.
     * @param inventory the inventory
     * @param itemStack the item stack
     */
    public static void fillEmpty(@NotNull Inventory inventory, @Nullable ItemStack itemStack){
        Condition.argNotNull("inventory", inventory);
        for(var i = 0; i < inventory.getSize(); i++) {
            if(ItemUtil.isNull(inventory.getItem(i))) inventory.setItem(i, itemStack);
        }
    }

    /**
     * Merges the first inventory into the second.<br>
     * Items of the first inventory will be put one-by-one into all empty slots of the second one until full.
     * @param a the first inventory
     * @param b the second inventory
     */
    public static void merge(@NotNull Inventory a, @NotNull Inventory b){
        Condition.argNotNull("a", a);
        Condition.argNotNull("b", b);
        var i = 0;
        for(var j = 0; j < b.getSize() && i < a.getSize(); j++) {
            if(ItemUtil.isNull(b.getItem(j))) {
                ItemStack item;
                while(ItemUtil.isNull((item = a.getItem(i)))) i++;
                b.setItem(j, item);
            }
        }
    }
}
