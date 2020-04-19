package dev.anhcraft.craftkit.cb_common.internal.backend;

import org.bukkit.plugin.PluginLoader;

public interface CBServerBackend extends IBackend {
    int getReloadCount();
    boolean isRunning();
    double[] getTPS();
    <T extends PluginLoader> T getPluginLoader(Class<T> clazz);
}
