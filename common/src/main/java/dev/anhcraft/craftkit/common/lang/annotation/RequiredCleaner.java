package dev.anhcraft.craftkit.common.lang.annotation;

import java.lang.annotation.*;

/**
 * To indicate which {@code static} fields need to be cleaned automatically. Any unavailable or deprecated objects (like players, worlds, servers, etc) are removed (even they are parts of structure objects such as map or collections).<br>
 * With fields, unavailable values will be replaced by nulls.<br>
 * With collections and arrays, their unavailable elements will be removed (by default) or you can choose to make them be null.<br>
 * With map, unavailable keys always make theirs entries to be removed. But it is optional for unavailable values, they can choose to be null instead of removing their entries.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredCleaner {
    /**
     * Allows the cleaner to remove unavailable elements in collections or arrays.<br>
     * If it is not allowed, those elements will be replaced by null values.
     * @return {@code true} or {@code false}
     */
    boolean allowRemoveElement() default true;

    /**
     * Allows the cleaner to remove unavailable entries if their values are unavailable.<br>
     * If it is not allowed, those values will be replaced by null values.
     * @return {@code true} or {@code false}
     */
    boolean allowRemoveEntryOnValue() default true;
}