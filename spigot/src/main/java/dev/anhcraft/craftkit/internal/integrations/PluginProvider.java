package dev.anhcraft.craftkit.internal.integrations;

import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class PluginProvider<V extends JavaPlugin> {
    private static final HashMap<Class<? extends PluginProvider>, PluginProvider> providers = new HashMap<>();

    public static <T extends PluginProvider> T getProvider(Class<T> clazz){
        T ins = (T) ReflectionUtil.invokeDeclaredConstructor(clazz);
        providers.putIfAbsent(clazz, ins);
        return ins;
    }

    protected V instance;

    public abstract boolean init();

    public Optional<V> getPluginInstance(){
        return Optional.ofNullable(instance);
    }
}
