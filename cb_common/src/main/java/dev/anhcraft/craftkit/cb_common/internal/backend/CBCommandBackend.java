package dev.anhcraft.craftkit.cb_common.internal.backend;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public interface CBCommandBackend extends IBackend {
    void register(JavaPlugin plugin, PluginCommand command);
    Map<String, Command> getKnownCommands();
    void unregister(Collection<String> commands);
    void unregister(Predicate<String> selector);
    void unregister(JavaPlugin plugin, PluginCommand command);
}
