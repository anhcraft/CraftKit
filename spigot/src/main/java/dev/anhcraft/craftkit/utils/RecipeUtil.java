package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.craftkit.cb_common.internal.backend.BackendManager;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBRecipeBackend;
import dev.anhcraft.craftkit.cb_common.internal.utils.ReflectUtil;
import dev.anhcraft.jvmkit.lang.annotation.Beta;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Extra methods for working with recipes.
 */
public class RecipeUtil {
    private static final CBRecipeBackend SERVICE = BackendManager.request(CBRecipeBackend.class).orElseThrow(UnsupportedOperationException::new);

    /**
     * Registers the given recipe.
     * @param recipe the recipe
     */
    public static void register(@NotNull Recipe recipe){
        Bukkit.addRecipe(recipe);
    }

    /**
     * Unregisters the given recipe.
     * @param recipe the recipe
     */
    @Beta
    public static void unregister(@NotNull Recipe recipe){
        SERVICE.removeIf(r -> compare(r, recipe));
    }

    /**
     * Checks if the given recipe is registered.
     * @param recipe the recipe
     * @return {@code true} if it was or {@code false} otherwise
     */
    @Beta
    public static boolean isRegistered(@NotNull Recipe recipe){
        return SERVICE.anyMatch(r -> compare(r, recipe));
    }

    /**
     * Compares two given recipe.
     * @param recipe the first recipe
     * @param otherRecipe the second recipe
     * @return {@code true} if they are equal or {@code false} otherwise
     */
    public static boolean compare(@Nullable Recipe recipe, @Nullable Recipe otherRecipe){
        if(recipe == null && otherRecipe == null) return true;
        if(recipe == null || otherRecipe == null) return false;
        if(!recipe.getClass().equals(otherRecipe.getClass())){
            return false;
        }
        if(NMSVersion.current().compare(NMSVersion.v1_12_R1) >= 0 && recipe instanceof Keyed && otherRecipe instanceof Keyed){
            Object v = ReflectUtil.getFieldValue(recipe.getClass(), "key", recipe);
            if(v != null) {
                return v.equals(ReflectUtil.getFieldValue(otherRecipe.getClass(), "key", otherRecipe));
            }
        }
        if(recipe instanceof ShapedRecipe && otherRecipe instanceof ShapedRecipe) {
            ShapedRecipe a = (ShapedRecipe) recipe;
            ShapedRecipe b = (ShapedRecipe) otherRecipe;
            if(ItemUtil.compare(a.getResult(), b.getResult())){
                List<ItemStack> ai = new ArrayList<>();
                List<ItemStack> bi = new ArrayList<>();
                for(String as : a.getShape()){
                    for(char ac : as.toCharArray()) ai.add(a.getIngredientMap().get(ac));
                }
                for(String bs : b.getShape()){
                    for(char bc : bs.toCharArray()) bi.add(b.getIngredientMap().get(bc));
                }
                return ItemUtil.compare(ai, bi);
            }
        }
        else if(recipe instanceof ShapelessRecipe && otherRecipe instanceof ShapelessRecipe){
            ShapelessRecipe a = (ShapelessRecipe) recipe;
            ShapelessRecipe b = (ShapelessRecipe) otherRecipe;
            if(ItemUtil.compare(a.getResult(), b.getResult()))
                return ItemUtil.compare(a.getIngredientList(), b.getIngredientList());
        }
        else if(recipe instanceof FurnaceRecipe && otherRecipe instanceof FurnaceRecipe){
            FurnaceRecipe a = (FurnaceRecipe) recipe;
            FurnaceRecipe b = (FurnaceRecipe) otherRecipe;
            return a.getExperience() == b.getExperience()
                    && ItemUtil.compare(a.getInput(), b.getInput())
                    && ItemUtil.compare(a.getResult(), b.getResult());
        }
        else if(recipe instanceof MerchantRecipe && otherRecipe instanceof MerchantRecipe){
            MerchantRecipe a = (MerchantRecipe) recipe;
            MerchantRecipe b = (MerchantRecipe) otherRecipe;
            return a.hasExperienceReward() == b.hasExperienceReward() && a.getMaxUses() == b.getMaxUses() && a.getUses() == b.getUses() && ItemUtil.compare(a.getResult(), b.getResult()) && ItemUtil.compare(a.getIngredients(), b.getIngredients());
        }
        return false;
    }
}
