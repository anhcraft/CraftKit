package dev.anhcraft.craftkit;

import dev.anhcraft.craftkit.common.ICraftExtension;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.internal.listeners.ExtensionListener;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A CraftKit extension.
 */
public class CraftExtension implements ICraftExtension<JavaPlugin> {
    private static final Map<Class<? extends JavaPlugin>, CraftExtension> REGISTRY = new HashMap<>();

    /**
     * Unregisters an extension.
     * @param mainClass the main class of the extension
     */
    public static void unregister(@NotNull Class<? extends JavaPlugin> mainClass){
        Condition.argNotNull("mainClass", mainClass);
        Condition.check(Bukkit.isPrimaryThread(), "Async catch! Require calling from main thread!");
        Condition.check(!CraftKit.class.isAssignableFrom(mainClass), "Can't get CraftExtension from CraftKit");
        REGISTRY.remove(mainClass);
    }

    /**
     * Gets the extension or register it (if not exists)
     * @param mainClass the main class of the extension
     * @return {@link CraftExtension}
     */
    @NotNull
    public static CraftExtension of(@NotNull Class<? extends JavaPlugin> mainClass){
        Condition.argNotNull("mainClass", mainClass);
        Condition.check(Bukkit.isPrimaryThread(), "Async catch! Require calling from main thread!");
        Condition.check(!CraftKit.class.isAssignableFrom(mainClass), "Can't get CraftExtension from CraftKit");

        CraftExtension ext = REGISTRY.get(mainClass);
        if(ext == null){
            ext = new CraftExtension(JavaPlugin.getPlugin(mainClass));
            Bukkit.getPluginManager().registerEvents(new ExtensionListener(ext), ext.getPlugin());
            REGISTRY.put(mainClass, ext);
        }
        return ext;
    }

    private JavaPlugin plugin;
    private TaskHelper taskHelper;

    private CraftExtension(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public TaskHelper getTaskHelper(){
        return taskHelper != null ? taskHelper : (taskHelper = new TaskHelper(plugin));
    }

    @Override
    @NotNull
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
