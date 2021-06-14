package dev.anhcraft.craftkit.cb_1_17_r1.services;

import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBRecipeBackend;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public class RecipeBackend extends CBModule implements CBRecipeBackend {
    @Override
    public void removeIf(Predicate<Recipe> filter) {
        for(Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>> type : minecraftServer.getCraftingManager().c.values()){
            type.values().removeIf(recipe -> filter.test(recipe.toBukkitRecipe()));
        }
    }

    @Override
    public boolean anyMatch(Predicate<Recipe> filter) {
        return minecraftServer.getCraftingManager().b().stream().anyMatch(ir -> filter.test(ir.toBukkitRecipe()));
    }
}
