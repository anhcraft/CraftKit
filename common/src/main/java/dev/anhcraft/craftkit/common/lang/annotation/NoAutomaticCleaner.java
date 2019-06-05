package dev.anhcraft.craftkit.common.lang.annotation;

import java.lang.annotation.*;

/**
 * Oops... the targets can not clean itself, you must do that manually.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface NoAutomaticCleaner {
}
