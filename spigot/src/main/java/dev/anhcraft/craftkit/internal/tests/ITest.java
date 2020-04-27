package dev.anhcraft.craftkit.internal.tests;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ITest {
    @NotNull
    String name();
    void run(@NotNull Player player, @NotNull TestChain chain);
}
