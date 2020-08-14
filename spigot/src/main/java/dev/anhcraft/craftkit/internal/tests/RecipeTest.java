package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.utils.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class RecipeTest implements ITest {
    @Override
    public @NotNull String name() {
        return "Recipe Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        ShapedRecipe recipe = new ShapedRecipe(
                new NamespacedKey(CraftExtension.of(CraftKit.class).getPlugin(), "test"),
                new ItemBuilder(Material.DIAMOND_SWORD)
                        .enchant(Enchantment.DAMAGE_ALL, 1)
                        .enchant(Enchantment.FIRE_ASPECT, 1)
                        .build()
        );
        recipe.shape("abc");
        recipe.setIngredient('a', Material.DIAMOND);
        recipe.setIngredient('b', Material.FIRE_CHARGE);
        recipe.setIngredient('c', Material.STICK);
        RecipeUtil.register(recipe);
        if(RecipeUtil.isRegistered(recipe)) {
            RecipeUtil.unregister(recipe);
            if(RecipeUtil.isRegistered(recipe)) {
                chain.report(false, "Isn't recipe already unregistered?");
            } else {
                chain.report(true, null);
            }
        } else {
            chain.report(false, "Failed to register recipe");
        }
    }
}
