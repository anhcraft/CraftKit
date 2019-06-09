package dev.anhcraft.craftkit.cb_common.internal;

import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public interface CBRecipeService extends CBService {
    void removeIf(Predicate<Recipe> filter);
    boolean anyMatch(Predicate<Recipe> filter);
}
