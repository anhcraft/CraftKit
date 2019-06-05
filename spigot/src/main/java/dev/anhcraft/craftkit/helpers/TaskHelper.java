package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.common.helpers.ITaskHelper;
import dev.anhcraft.jvmkit.lang.annotation.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * This helper gives you universal ways to work with tasks, which are same on both Spigot and Bungeecord servers.
 */
public class TaskHelper implements ITaskHelper {
    private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
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
        Condition.argNotNull("task", task);
        return SCHEDULER.runTask(plugin, task).getTaskId();
    }

    @Override
    public int newAsyncTask(@NotNull Runnable task) {
        Condition.argNotNull("task", task);
        return SCHEDULER.runTaskAsynchronously(plugin, task).getTaskId();
    }

    @Override
    public int newDelayedTask(@NotNull Runnable task, long delay) {
        Condition.argNotNull("task", task);
        return SCHEDULER.runTaskLater(plugin, task, delay).getTaskId();
    }

    @Override
    public int newDelayedAsyncTask(@NotNull Runnable task, long delay) {
        Condition.argNotNull("task", task);
        return SCHEDULER.runTaskLaterAsynchronously(plugin, task, delay).getTaskId();
    }

    @Override
    public int newTimerTask(@NotNull Runnable task, long delay, long interval) {
        Condition.argNotNull("task", task);
        return SCHEDULER.runTaskTimer(plugin, task, delay, interval).getTaskId();
    }

    @Override
    public int newAsyncTimerTask(@NotNull Runnable task, long delay, long interval) {
        Condition.argNotNull("task", task);
        return SCHEDULER.runTaskTimerAsynchronously(plugin, task, delay, interval).getTaskId();
    }

    @Override
    public void cancelTask(int id) {
        SCHEDULER.cancelTask(id);
    }

    @Override
    public boolean isRunning(int id) {
        return SCHEDULER.isCurrentlyRunning(id);
    }
}
