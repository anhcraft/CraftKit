package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a plugin helper implementation.
 */
public class AbstractPluginHelper {
    protected Plugin plugin;

    /**
     * Constructs an instance of {@code AbstractPluginHelper} with the given plugin.
     * @param plugin the plugin
     */
    public AbstractPluginHelper(@NotNull Plugin plugin) {
        Condition.argNotNull("plugin", plugin);
        this.plugin = plugin;
    }

    /**
     * Returns the plugin which this helper is working for.
     * @return the plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }
}
