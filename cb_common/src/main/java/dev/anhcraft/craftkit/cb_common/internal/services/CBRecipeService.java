package dev.anhcraft.craftkit.cb_common.internal.services;

import org.bukkit.inventory.Recipe;

import java.util.function.Predicate;

public interface CBRecipeService extends CBService {
    void removeIf(Predicate<Recipe> filter);
    boolean anyMatch(Predicate<Recipe> filter);
}
