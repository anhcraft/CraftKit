package dev.anhcraft.craftkit.cb_1_14_r1.services;

import dev.anhcraft.craftkit.cb_1_14_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBCommandService;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class CommandService extends CBModule implements CBCommandService {
    @Override
    public void register(JavaPlugin plugin, PluginCommand command) {
        craftServer.getCommandMap().register(plugin.getName(), command);
    }

    @Override
    public Map<String, Command> getKnownCommands() {
        return (Map<String, Command>) ReflectionUtil.getDeclaredField(SimpleCommandMap.class, craftServer.getCommandMap(), "knownCommands");
    }

    @Override
    public void unregister(List<String> commands) {
        getKnownCommands().keySet().removeAll(commands);
    }

    @Override
    public void unregister(Predicate<String> selector) {
        getKnownCommands().keySet().removeIf(selector);
    }

    @Override
    public void unregister(JavaPlugin plugin, PluginCommand command) {
        Map<String, Command> knownCommands = getKnownCommands();

        knownCommands.remove((plugin.getName()+":"+command.getName()).toLowerCase());

        if(!command.getLabel().contains(":")) knownCommands.remove(command.getLabel());

        var activeAliases = command.getAliases();
        for (String alias : activeAliases) knownCommands.remove(alias);

        List<String> registeredAliases = (List<String>) ReflectionUtil.getDeclaredField(Command.class, command, "aliases");
        for (String alias : registeredAliases) knownCommands.remove((plugin.getName()+":"+alias).toLowerCase());

        command.unregister(craftServer.getCommandMap());
    }
}
