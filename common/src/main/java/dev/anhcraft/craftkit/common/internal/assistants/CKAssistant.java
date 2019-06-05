package dev.anhcraft.craftkit.common.internal.assistants;

import dev.anhcraft.craftkit.common.lang.annotation.RequiredCleaner;
import dev.anhcraft.jvmkit.utils.Condition;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CKAssistant {
    static final Map<Class<? extends Annotation>, List<Field>> INDEXED_FIELDS = new ConcurrentHashMap<>();
    static final Map<Class<? extends Annotation>, List<Method>> INDEXED_METHODS = new ConcurrentHashMap<>();

    private static void registerAnnotation(Class<? extends Annotation> c){
        Condition.check(c.isAnnotationPresent(Target.class), "annotation <target> must be assigned");
        var targets = c.getAnnotation(Target.class).value();

        for(var t : targets){
            if(t.equals(ElementType.FIELD)) INDEXED_FIELDS.put(c, new ArrayList<>());
            else if(t.equals(ElementType.METHOD))INDEXED_METHODS.put(c, new ArrayList<>());
        }
    }

    static {
        registerAnnotation(RequiredCleaner.class);
    }

    public static void cleanIndexes(){
        INDEXED_FIELDS.clear();
        INDEXED_METHODS.clear();
    }

    public static void doIndex(Collection<Class<?>> classes){
        for (var clazz : classes) {
            Field[] fields = new Field[0];
            try {
                fields = clazz.getDeclaredFields();
            } catch (NoClassDefFoundError ignored) {
            }
            for (Field f : fields) {
                f.setAccessible(true);
                if (!Modifier.isStatic(f.getModifiers())) continue;
                for (Map.Entry<Class<? extends Annotation>, List<Field>> a : INDEXED_FIELDS.entrySet()) {
                    if (f.isAnnotationPresent(a.getKey())) a.getValue().add(f);
                }
            }

            Method[] methods = new Method[0];
            try {
                methods = clazz.getDeclaredMethods();
            } catch (NoClassDefFoundError ignored) {
            }
            for (var m : methods) {
                m.setAccessible(true);
                if (!Modifier.isStatic(m.getModifiers())) continue;
                for (var a : INDEXED_METHODS.entrySet()) {
                    if (m.isAnnotationPresent(a.getKey())) a.getValue().add(m);
                }
            }
        }
    }
}
