package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.internal.CBCommandService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Extra methods for working with commands.
 */
public class CommandUtil {
    private static final CBCommandService SERVICE = CBProvider.getService(CBCommandService.class).orElseThrow(UnsupportedOperationException::new);

    /**
     * Gets all known commands (include vanilla/bukkit commands).
     * @return an immutable map of known commands
     */
    @NotNull
    public static Map<String, Command> getKnownCommands() {
        return Collections.unmodifiableMap(SERVICE.getKnownCommands());
    }

    /**
     * Registers the given {@link PluginCommand} without declaring in <b>plugin.yml</b>.
     * @param plugin the plugin which owns the command
     * @param command the command
     */
    public static void register(@NotNull JavaPlugin plugin, @NotNull PluginCommand command) {
        Condition.argNotNull("plugin", plugin);
        Condition.argNotNull("command", command);
        SERVICE.register(plugin, command);
    }

    /**
     * Unregisters the given {@link PluginCommand}.
     * @param plugin the plugin which owns the command
     * @param command the command
     */
    public static void unregister(@NotNull JavaPlugin plugin, @NotNull PluginCommand command) {
        Condition.argNotNull("plugin", plugin);
        Condition.argNotNull("command", command);
        SERVICE.unregister(plugin, command);
    }

    /**
     * Unregisters given commands.
     * @param commands list of commands to be removed (strings must be lowercase)
     */
    public static void unregister(@NotNull List<String> commands) {
        Condition.argNotNull("commands", commands);
        SERVICE.unregister(commands);
    }
}
