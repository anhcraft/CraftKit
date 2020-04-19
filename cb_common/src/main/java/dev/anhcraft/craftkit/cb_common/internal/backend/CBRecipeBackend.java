package dev.anhcraft.craftkit.cb_common.internal.backend;

import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public interface CBRecipeBackend extends IBackend {
    void removeIf(Predicate<Recipe> filter);
    boolean anyMatch(Predicate<Recipe> filter);
}
