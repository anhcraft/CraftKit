package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.internal.backend.CBCommandBackend;
import dev.anhcraft.craftkit.cb_common.internal.backend.BackendManager;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Extra methods for working with commands.
 */
public class CommandUtil {
    private static final CBCommandBackend SERVICE = BackendManager.request(CBCommandBackend.class).orElseThrow(UnsupportedOperationException::new);

    @NotNull
    public static PluginCommand createPluginCommand(@NotNull String cmd, @NotNull Plugin plugin){
        return (PluginCommand) ReflectionUtil.invokeDeclaredConstructor(PluginCommand.class,
                new Class[]{String.class, Plugin.class},
                new Object[]{cmd, plugin}
        );
    }

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
    public static void unregister(@NotNull Collection<String> commands) {
        Condition.argNotNull("commands", commands);
        SERVICE.unregister(commands);
    }
}
