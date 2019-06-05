package dev.anhcraft.craftkit.common.internal.assistants;

import dev.anhcraft.craftkit.common.lang.annotation.RequiredCleaner;
import dev.anhcraft.jvmkit.utils.ArrayUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class CKCleaner {
    public static void clean(Predicate<Object> filter){
        try {
            List<Field> fields = CKAssistant.INDEXED_FIELDS.get(RequiredCleaner.class);
            for (Field f : fields) {
                RequiredCleaner rc = f.getAnnotation(RequiredCleaner.class);
                var v = f.get(null);
                if (filter.test(v)) f.set(null, null);
                else if (f.getType().isArray()) {
                    var len = Array.getLength(v);
                    if (rc.allowRemoveElement()) {
                        Object[] av = (Object[]) v;
                        for (var i = 0; i < len; i++) {
                            if (filter.test(av[i])) {
                                av = ArrayUtil.remove(av, i);
                                i--;
                                len--;
                            }
                        }
                        v = av;
                    } else {
                        for (var i = 0; i < len; i++) {
                            if (filter.test(Array.get(v, i))) Array.set(v, i, null);
                        }
                    }
                    f.set(null, v);
                } else if (Collection.class.isAssignableFrom(f.getType())) {
                    Collection c = (Collection) v;
                    if (rc.allowRemoveElement()) c.removeIf(filter);
                    else
                        c.stream().map(o -> filter.test(o) ? null : o).collect(Collectors.toCollection((Supplier<Collection<Object>>) () -> c));
                    f.set(null, c);
                } else if (Map.class.isAssignableFrom(f.getType())) {
                    Map m = (Map) v;
                    for (Object k : m.keySet()) {
                        if (filter.test(k)) m.remove(k);
                    }

                    if (rc.allowRemoveEntryOnValue())
                        m.entrySet().removeIf(ent -> filter.test(((Map.Entry) ent).getValue()));
                    else {
                        for (Object k : m.keySet()) {
                            if (filter.test(m.get(k))) m.put(k, null);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}