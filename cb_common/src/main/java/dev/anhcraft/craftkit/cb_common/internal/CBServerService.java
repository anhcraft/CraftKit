package dev.anhcraft.craftkit.cb_common.internal;

import org.bukkit.plugin.PluginLoader;

public interface CBServerService extends CBService {
    int getReloadCount();
    boolean isRunning();
    double[] getTPS();
    <T extends PluginLoader> T getPluginLoader(Class<T> clazz);
}
