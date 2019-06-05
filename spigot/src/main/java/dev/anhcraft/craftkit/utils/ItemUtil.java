package dev.anhcraft.craftkit.utils;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Extra methods for working with items.
 */
public class ItemUtil {
    /**
     * Checks if the given item stack is null or its type is {@link Material#AIR}.
     * @param item an item stack
     * @return {@code true} if it is "null". Otherwise is {@code false}.
     */
    public static boolean isNull(ItemStack item){
        return item == null || item.getType() == Material.AIR || item.getType().toString().endsWith("_AIR");
    }

    /**
     * Checks if the given material is null or is air.
     * @param material material
     * @return {@code true} if it is "null". Otherwise is {@code false}.
     */
    public static boolean isNull(Material material) {
        return material == null || material == Material.AIR || material.toString().endsWith("_AIR");
    }

    /**
     * Compares two given item stacks.
     * @param a the first item stack
     * @param b the second item stack
     * @return {@code true} if they are equal. Otherwise is {@code false}.
     */
    public static boolean compare(ItemStack a, ItemStack b) {
        if(isNull(a)) return isNull(b);
        else if(isNull(b)) return isNull(a);
        return a.equals(b);
    }

    /**
     * Compares two given array of item stacks.
     * @param a the first array
     * @param b the second array
     * @return {@code true} if they are equal. Otherwise is {@code false}.
     */
    public static boolean compare(@NotNull ItemStack[] a, @NotNull ItemStack[] b) {
        Condition.argNotNull("a", a);
        Condition.argNotNull("b", b);
        if(a.length != b.length) return false;
        var i = 0;
        while (i < a.length){
            if(!compare(a[i], b[i])) return false;
            i++;
        }
        return true;
    }

    /**
     * Clones the given item safely.
     * @param item an items tack
     * @return the copy of given item
     */
    public static ItemStack clone(ItemStack item) {
        return item == null ? null : item.clone();
    }
}
