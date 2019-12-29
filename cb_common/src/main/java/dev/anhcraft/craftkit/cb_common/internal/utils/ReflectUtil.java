package dev.anhcraft.craftkit.cb_common.internal.utils;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class ReflectUtil {
    @Nullable
    public static Object getFieldValue(Class<?> clazz, String field, Object obj){
        try {
            Field f = clazz.getField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
