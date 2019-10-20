package dev.anhcraft.craftkit;

import dev.anhcraft.craftkit.cb_common.internal.services.CBAnvilService;
import dev.anhcraft.craftkit.cb_common.internal.services.CBCustomGUIService;
import dev.anhcraft.craftkit.cb_common.internal.services.ServiceProvider;
import dev.anhcraft.craftkit.cb_common.gui.AnvilGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.common.ICraftExtension;
import dev.anhcraft.craftkit.entity.CustomEntity;
import dev.anhcraft.craftkit.entity.TrackedEntity;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A CraftKit extension.
 */
public class CraftExtension implements ICraftExtension<JavaPlugin> {
    private static final CBAnvilService SERVICE_1 = ServiceProvider.getService(CBAnvilService.class).orElseThrow(UnsupportedOperationException::new);
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
    private final List<TrackedEntity> trackedEntities = new CopyOnWriteArrayList<>();

    private CraftExtension(JavaPlugin plugin){
        this.plugin = plugin;
        taskHelper = new TaskHelper(plugin);
        taskHelper.newTimerTask(() -> {
            for (Iterator<TrackedEntity> it = trackedEntities.iterator(); it.hasNext(); ) {
                TrackedEntity e = it.next();
                if(e.isDead()){
                    it.remove();
                    continue;
                }
                e.updateView();
            }
        }, 0, 20);
    }

    @Override
    @NotNull
    public TaskHelper getTaskHelper(){
        return taskHelper;
    }

    @NotNull
    public CustomGUI createCustomGUI(@Nullable InventoryHolder holder, int size, @Nullable String title){
        size = (int) MathUtil.nextMultiple(size, 9);
        return ServiceProvider.getService(CBCustomGUIService.class,
                new Class<?>[]{InventoryHolder.class, int.class, String.class},
                new Object[]{holder, size, title},
                false).orElseThrow(UnsupportedOperationException::new);
    }

    @NotNull
    public AnvilGUI createAnvilGUI(@NotNull Player player, @NotNull String title){
        Condition.argNotNull("player", player);
        Condition.argNotNull("title", title);
        return (AnvilGUI) SERVICE_1.create(player, title).getTopInventory();
    }

    @NotNull
    public <T extends CustomEntity> TrackedEntity<T> trackEntity(@NotNull T entity){
        Condition.argNotNull("entity", entity);
        TrackedEntity<T> x = new TrackedEntity<>(entity);
        trackedEntities.add(x);
        return x;
    }

    public void untrackEntity(@NotNull TrackedEntity<? extends CustomEntity> entity){
        Condition.argNotNull("entity", entity);
        trackedEntities.remove(entity);
    }

    @Override
    @NotNull
    public JavaPlugin getPlugin() {
        return plugin;
    }
}