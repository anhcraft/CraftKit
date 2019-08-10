package dev.anhcraft.craftkit.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    public static boolean compare(@Nullable ItemStack a, @Nullable ItemStack b) {
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
    public static boolean compare(@Nullable ItemStack[] a, @Nullable ItemStack[] b) {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;
        if(a.length != b.length) return false;
        int i = 0;
        while (i < a.length){
            if(!compare(a[i], b[i])) return false;
            i++;
        }
        return true;
    }

    /**
     * Compares two given list of item stacks.
     * @param a the first list
     * @param b the second list
     * @return {@code true} if they are equal. Otherwise is {@code false}.
     */
    public static boolean compare(@Nullable List<ItemStack> a, @Nullable List<ItemStack> b) {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;
        if(a.size() != b.size()) return false;
        int i = 0;
        while (i < a.size()){
            if(!compare(a.get(i), b.get(i))) return false;
            i++;
        }
        return true;
    }

    /**
     * Clones the given item safely.
     * @param item an items tack
     * @return the copy of given item
     */
    public static ItemStack clone(@Nullable ItemStack item) {
        return item == null ? null : item.clone();
    }
}
