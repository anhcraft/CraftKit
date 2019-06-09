package dev.anhcraft.craftkit.cb_1_14_r1.services;

import dev.anhcraft.craftkit.cb_1_14_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBRecipeService;
import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public class RecipeService extends CBModule implements CBRecipeService {
    @Override
    public void removeIf(Predicate<Recipe> filter) {
        minecraftServer.getCraftingManager().b().removeIf(ir -> filter.test(ir.toBukkitRecipe()));
    }

    @Override
    public boolean anyMatch(Predicate<Recipe> filter) {
        return minecraftServer.getCraftingManager().b().stream().anyMatch(ir -> filter.test(ir.toBukkitRecipe()));
    }
}
