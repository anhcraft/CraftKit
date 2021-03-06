package dev.anhcraft.craftkit.cb_1_9_r2.services;

import dev.anhcraft.craftkit.cb_1_9_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBRecipeBackend;
import net.minecraft.server.v1_9_R2.CraftingManager;
import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public class RecipeBackend extends CBModule implements CBRecipeBackend {
    @Override
    public void removeIf(Predicate<Recipe> filter) {
        CraftingManager.getInstance().getRecipes().removeIf(ir -> filter.test(ir.toBukkitRecipe()));
    }

    @Override
    public boolean anyMatch(Predicate<Recipe> filter) {
        return CraftingManager.getInstance().getRecipes()
                .stream().anyMatch(ir -> filter.test(ir.toBukkitRecipe()));
    }
}
