package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.common.helpers.ITaskHelper;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import gnu.trove.map.TIntObjectMap;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.scheduler.BungeeScheduler;
import net.md_5.bungee.scheduler.BungeeTask;

import java.util.concurrent.TimeUnit;

/**
 * This helper gives you universal ways to work with tasks, which are same on both Spigot and Bungeecord servers.
 */
public class TaskHelper implements ITaskHelper {
    private static final TaskScheduler SCHEDULER = ProxyServer.getInstance().getScheduler();
    private Plugin plugin;

    /**
     * Constructs an instance of {@code TaskHelper}.
     * @param plugin the plugin which owns all future tasks created from the instance
     */
    public TaskHelper(@NotNull Plugin plugin){
        Condition.argNotNull("plugin", plugin);
        this.plugin = plugin;
    }

    @Override
    public int newTask(@NotNull Runnable task) {
        return newAsyncTask(task);
    }

    @Override
    public int newAsyncTask(@NotNull Runnable task) {
        Condition.argNotNull("task", task);
        return SCHEDULER.runAsync(plugin, task).getId();
    }

    @Override
    public int newDelayedTask(@NotNull Runnable task, long delay) {
        return newDelayedAsyncTask(task, delay);
    }

    @Override
    public int newDelayedAsyncTask(@NotNull Runnable task, long delay) {
        Condition.argNotNull("task", task);
        return SCHEDULER.schedule(plugin, task, delay*50, TimeUnit.MILLISECONDS).getId();
    }

    @Override
    public int newTimerTask(@NotNull Runnable task, long delay, long interval) {
        return newAsyncTimerTask(task, delay, interval);
    }

    @Override
    public int newAsyncTimerTask(@NotNull Runnable task, long delay, long interval) {
        Condition.argNotNull("task", task);
        return SCHEDULER.schedule(plugin, task, delay*50, interval*50, TimeUnit.MILLISECONDS).getId();
    }

    @Override
    public void cancelTask(int id) {
        SCHEDULER.cancel(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isRunning(int id) {
        return ((TIntObjectMap<BungeeTask>) ReflectionUtil.getDeclaredField(BungeeScheduler.class, SCHEDULER, "tasks")).containsKey(id);
    }
}
