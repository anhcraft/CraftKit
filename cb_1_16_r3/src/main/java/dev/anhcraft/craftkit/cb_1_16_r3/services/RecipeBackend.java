package dev.anhcraft.craftkit.cb_1_16_r2.services;

import dev.anhcraft.craftkit.cb_1_16_r3.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBRecipeBackend;
import net.minecraft.server.v1_16_R3.IRecipe;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public class RecipeBackend extends CBModule implements CBRecipeBackend {
    @Override
    public void removeIf(Predicate<Recipe> filter) {
        for(Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>> type : minecraftServer.getCraftingManager().recipes.values()){
            type.values().removeIf(recipe -> filter.test(recipe.toBukkitRecipe()));
        }
    }

    @Override
    public boolean anyMatch(Predicate<Recipe> filter) {
        return minecraftServer.getCraftingManager().b().stream().anyMatch(ir -> filter.test(ir.toBukkitRecipe()));
    }
}
