package dev.anhcraft.craftkit.cb_common.internal.services;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ServiceProvider {
    private static final String NMS_SERVICE_PACKAGE = "dev.anhcraft.craftkit.cb_"+ NMSVersion.current().toString().substring(1).toLowerCase()+".services.";
    private static final Map<Class<? extends CBService>, Class<?>> CACHED_CLASS = new ConcurrentHashMap<>();
    private static final Map<Class<? extends CBService>, CBService> CACHED_SERVICE = new ConcurrentHashMap<>();

    private static <T extends CBService> Class<?> getNMSServiceClass(Class<T> clazz) throws ClassNotFoundException {
        Class<?> c = CACHED_CLASS.get(clazz);
        if(c == null){
            c = Class.forName(NMS_SERVICE_PACKAGE + clazz.getSimpleName().substring(2));
            CACHED_CLASS.put(clazz, c);
        }
        return c;
    }

    public static <S extends T, T extends CBService> Optional<S> getService(Class<T> clazz){
        return getService(clazz, true);
    }

    public static <S extends T, T extends CBService> Optional<S> getService(Class<T> clazz, boolean cache){
        CBService s;
        if(cache && (s = CACHED_SERVICE.get(clazz)) != null)
            return Optional.of((S) s);

        try {
            Class<?> nmsClass = getNMSServiceClass(clazz);
            S ins = (S) ReflectionUtil.invokeDeclaredConstructor(nmsClass);
            if(ins != null) {
                if (cache) CACHED_SERVICE.put(clazz, ins);
                return Optional.of(ins);
            }
        } catch (ClassNotFoundException ignored) {
        }
        return Optional.empty();
    }

    public static <S extends T, T extends CBService> Optional<S> getService(Class<T> clazz, Class<?>[] params, Object[] args, boolean cache){
        CBService s;
        if(cache && (s = CACHED_SERVICE.get(clazz)) != null)
            return Optional.of((S) s);

        try {
            Class<?> nmsClass = getNMSServiceClass(clazz);
            S ins = (S) ReflectionUtil.invokeDeclaredConstructor(nmsClass, params, args);
            if(ins != null) {
                if (cache) CACHED_SERVICE.put(clazz, ins);
                return Optional.of(ins);
            }
        } catch (ClassNotFoundException ignored) {
        }
        return Optional.empty();
    }
}
