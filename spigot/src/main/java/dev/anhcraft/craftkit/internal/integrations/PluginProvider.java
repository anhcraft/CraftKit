package dev.anhcraft.craftkit.internal.integrations;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class PluginProvider<V extends JavaPlugin> {
    private static final ClassToInstanceMap<PluginProvider<?>> PROVIDERS = MutableClassToInstanceMap.create();

    public static <T extends PluginProvider<?>> T getProvider(Class<T> clazz){
        T ins = (T) ReflectionUtil.invokeDeclaredConstructor(clazz);
        PROVIDERS.putIfAbsent(clazz, ins);
        return ins;
    }

    protected V instance;

    public abstract boolean init();

    public Optional<V> getPluginInstance(){
        return Optional.ofNullable(instance);
    }
}
