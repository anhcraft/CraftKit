package dev.anhcraft.craftkit.cb_1_16_r2.services;

import dev.anhcraft.craftkit.cb_1_16_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBRecipeBackend;
import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public class RecipeBackend extends CBModule implements CBRecipeBackend {
    @Override
    public void removeIf(Predicate<Recipe> filter) {
        minecraftServer.getCraftingManager().b().removeIf(ir -> filter.test(ir.toBukkitRecipe()));
    }

    @Override
    public boolean anyMatch(Predicate<Recipe> filter) {
        return minecraftServer.getCraftingManager().b().stream().anyMatch(ir -> filter.test(ir.toBukkitRecipe()));
    }
}
