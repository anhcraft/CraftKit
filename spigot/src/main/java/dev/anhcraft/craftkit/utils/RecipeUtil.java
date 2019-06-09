package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.internal.CBRecipeService;
import dev.anhcraft.jvmkit.lang.annotation.Beta;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Extra methods for working with recipes.
 */
public class RecipeUtil {
    private static final CBRecipeService SERVICE = CBProvider.getService(CBRecipeService.class).orElseThrow();

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
        // TODO Compare by namedspace key for 1.12+
        SERVICE.removeIf(r -> compare(r, recipe));
    }

    /**
     * Checks if the given recipe is registered.
     * @param recipe the recipe
     * @return {@code true} if it was or {@code false} otherwise
     */
    @Beta
    public static boolean isRegistered(@NotNull Recipe recipe){
        // TODO Compare by namedspace key for 1.12+
        return SERVICE.anyMatch(r -> compare(r, recipe));
    }

    /**
     * Compares two given recipe.
     * @param recipe the first recipe
     * @param otherRecipe the second recipe
     * @return {@code true} if they are equal or {@code false} otherwise
     */
    public static boolean compare(@Nullable Recipe recipe, @Nullable Recipe otherRecipe){
        if(recipe instanceof ShapedRecipe && otherRecipe instanceof ShapedRecipe) {
            var a = (ShapedRecipe) recipe;
            var b = (ShapedRecipe) otherRecipe;
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
            var a = (ShapelessRecipe) recipe;
            var b = (ShapelessRecipe) otherRecipe;
            if(ItemUtil.compare(a.getResult(), b.getResult()))
                return ItemUtil.compare(a.getIngredientList(), b.getIngredientList());
        }
        else if(recipe instanceof FurnaceRecipe && otherRecipe instanceof FurnaceRecipe){
            var a = (FurnaceRecipe) recipe;
            var b = (FurnaceRecipe) otherRecipe;
            return a.getExperience() == b.getExperience()
                    && ItemUtil.compare(a.getInput(), b.getInput())
                    && ItemUtil.compare(a.getResult(), b.getResult());
        }
        // TODO Support Stonecutting Recipe [1.14]
        return false;
    }
}
