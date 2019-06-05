package dev.anhcraft.craftkit.common.helpers;

import dev.anhcraft.jvmkit.lang.annotation.NotNull;

/**
 * Represents {@code TaskHelper} implementation.
 */
public interface ITaskHelper {
    /**
     * Executes the given task on the main thread.
     * @param task the task
     * @return the task's id
     */
    int newTask(@NotNull Runnable task);

    /**
     * Executes the given task asynchronously.
     * @param task the task
     * @return the task's id
     */
    int newAsyncTask(@NotNull Runnable task);

    /**
     * Executes the given task on the main thread after the {@code delay} time.
     * @param task the task
     * @param delay the delay time (in Minecraft ticks)
     * @return the task's id
     */
    int newDelayedTask(@NotNull Runnable task, long delay);

    /**
     * Executes the given task asynchronously after the {@code delay} time.
     * @param task the task
     * @param delay the delay time (in Minecraft ticks)
     * @return the task's id
     */
    int newDelayedAsyncTask(@NotNull Runnable task, long delay);

    /**
     * Waits for the {@code delay} time and then executes the given task on the main thread repeatedly.
     * @param task the task
     * @param delay the delay time (in Minecraft ticks)
     * @param interval the waiting time between two repeats
     * @return the task's id
     */
    int newTimerTask(@NotNull Runnable task, long delay, long interval);

    /**
     * Waits for the {@code delay} time and then executes the given task repeatedly.<br>
     * The task will be performed asynchronously.
     * @param task the task
     * @param delay the delay time (in Minecraft ticks)
     * @param interval the waiting time between two repeats (in Minecraft ticks)
     * @return the task's id
     */
    int newAsyncTimerTask(@NotNull Runnable task, long delay, long interval);

    /**
     * Cancels a task.
     * @param id the task's id
     */
    void cancelTask(int id);

    /**
     * Checks if a task is still running.
     * @param id the task's id
     * @return {@code true} if it is or {@code false} if not
     */
    boolean isRunning(int id);
}
