package dev.anhcraft.craftkit.cb_common.internal;

import dev.anhcraft.craftkit.cb_common.lang.enumeration.NMSVersion;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class CBProvider {
    private static final String NMS_SERVICE_PACKAGE = "dev.anhcraft.craftkit.cb_"+ NMSVersion.getNMSVersion().toString().substring(1).toLowerCase()+".services.";
    private static final Map<Class<? extends CBService>, CBService> CACHED_SERVICE = new ConcurrentHashMap<>();

    public static <S extends T, T extends CBService> Optional<S> getService(Class<T> clazz, boolean cache){
        if(cache && CACHED_SERVICE.containsKey(clazz)) return Optional.of((S) CACHED_SERVICE.get(clazz));
        try {
            Class<?> nmsClass = Class.forName(NMS_SERVICE_PACKAGE + clazz.getSimpleName().substring(2));
            S ins = (S) ReflectionUtil.invokeDeclaredConstructor(nmsClass);
            if(cache) CACHED_SERVICE.put(clazz, ins);
            return Optional.of(ins);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static <S extends T, T extends CBService> Optional<S> getService(Class<T> clazz){
        return getService(clazz, true);
    }
}
