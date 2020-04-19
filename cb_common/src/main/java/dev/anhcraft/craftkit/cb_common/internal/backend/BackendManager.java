package dev.anhcraft.craftkit.cb_common.internal.backend;

import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class BackendManager {
    private static final String NMS_SERVICE_PACKAGE = "dev.anhcraft.craftkit.cb_"+ NMSVersion.current().toString().substring(1).toLowerCase()+".services.";
    private static final Map<Class<? extends IBackend>, Class<?>> CACHED_CLASS = new ConcurrentHashMap<>();
    private static final Map<Class<? extends IBackend>, IBackend> CACHED_SERVICE = new ConcurrentHashMap<>();

    private static <T extends IBackend> Class<?> getNMSServiceClass(Class<T> clazz) throws ClassNotFoundException {
        Class<?> c = CACHED_CLASS.get(clazz);
        if(c == null){
            c = Class.forName(NMS_SERVICE_PACKAGE + clazz.getSimpleName().substring(2));
            CACHED_CLASS.put(clazz, c);
        }
        return c;
    }

    public static <S extends T, T extends IBackend> Optional<S> request(Class<T> clazz){
        return request(clazz, true);
    }

    public static <S extends T, T extends IBackend> Optional<S> request(Class<T> clazz, boolean cache){
        IBackend s;
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

    public static <S extends T, T extends IBackend> Optional<S> request(Class<T> clazz, Class<?>[] params, Object[] args, boolean cache){
        IBackend s;
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
