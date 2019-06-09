package dev.anhcraft.craftkit.cb_1_12_r1.services;

import dev.anhcraft.craftkit.cb_1_12_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBRecipeService;
import net.minecraft.server.v1_12_R1.CraftingManager;
import net.minecraft.server.v1_12_R1.IRecipe;
import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public class RecipeService extends CBModule implements CBRecipeService {
    @Override
    public void removeIf(Predicate<Recipe> filter) {
        var v = CraftingManager.recipes.iterator();
        while(v.hasNext()){
            var r = v.next();
            if(filter.test(r.toBukkitRecipe())) v.remove();
        }
    }

    @Override
    public boolean anyMatch(Predicate<Recipe> filter) {
        for (IRecipe r : CraftingManager.recipes) {
            if (filter.test(r.toBukkitRecipe())) return true;
        }
        return false;
    }
}
