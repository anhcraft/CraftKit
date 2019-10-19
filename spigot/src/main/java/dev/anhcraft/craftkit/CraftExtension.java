package dev.anhcraft.craftkit;

import dev.anhcraft.craftkit.cb_common.internal.CBCustomInventoryService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.inventory.CustomInventory;
import dev.anhcraft.craftkit.common.ICraftExtension;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @NotNull
    public CustomInventory createInventory(@Nullable InventoryHolder holder, int size, @Nullable String title){
        size = (int) MathUtil.nextMultiple(size, 9);
        return CBProvider.getService(CBCustomInventoryService.class,
                new Class<?>[]{InventoryHolder.class, int.class, String.class},
                new Object[]{holder, size, title},
                false).orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    @NotNull
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
