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
        for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, itemStack);
    }

    /**
     * Fills all empty slots in {@code inventory} with the given item stack.
     * @param inventory the inventory
     * @param itemStack the item stack
     */
    public static void fillEmpty(@NotNull Inventory inventory, @Nullable ItemStack itemStack){
        Condition.argNotNull("inventory", inventory);
        for(int i = 0; i < inventory.getSize(); i++) {
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
        int i = 0;
        for(int j = 0; j < b.getSize() && i < a.getSize(); j++) {
            if(ItemUtil.isNull(b.getItem(j))) {
                ItemStack item;
                while(ItemUtil.isNull((item = a.getItem(i)))) i++;
                b.setItem(j, item);
            }
        }
    }
}
