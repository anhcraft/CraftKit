package dev.anhcraft.craftkit.cb_1_11_r1.services;

import dev.anhcraft.craftkit.cb_1_11_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBServerService;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.SimplePluginManager;

import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class ServerService extends CBModule implements CBServerService {
    @Override
    public int getReloadCount() {
        return craftServer.reloadCount;
    }

    @Override
    public boolean isRunning() {
        return minecraftServer.isRunning();
    }

    @Override
    public double[] getTPS() {
        return minecraftServer.recentTps;
    }

    @Override
    public <T extends PluginLoader> T getPluginLoader(Class<T> clazz) {
        var spm = (SimplePluginManager) craftServer.getPluginManager();
        var loaders = ((Map<Pattern, PluginLoader>) ReflectionUtil.getDeclaredField(SimplePluginManager.class, spm, "fileAssociations")).values();
        for (PluginLoader loader : loaders) {
            if (loader.getClass().equals(clazz)) return (T) loader;
        }
        return null;
    }
}
