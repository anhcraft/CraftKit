package dev.anhcraft.craftkit.common;

import dev.anhcraft.craftkit.common.helpers.ITaskHelper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents CraftKit extension.
 */
public interface ICraftExtension<T> {
    @NotNull
    ITaskHelper getTaskHelper();

    @NotNull
    T getPlugin();
}
