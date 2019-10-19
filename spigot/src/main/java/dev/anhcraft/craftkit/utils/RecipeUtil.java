package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.internal.services.ServiceProvider;
import dev.anhcraft.craftkit.cb_common.internal.services.CBRecipeService;
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
/*
 TODO Compare by namedspace key for 1.12+
 - This must be optional since existing recipe can use random namespace

 TODO Support comparison for other recipes that are newly added in 1.14
 */
public class RecipeUtil {
    private static final CBRecipeService SERVICE = ServiceProvider.getService(CBRecipeService.class).orElseThrow(UnsupportedOperationException::new);

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
        // TODO Support other Recipe types [1.14]
        return false;
    }
}
