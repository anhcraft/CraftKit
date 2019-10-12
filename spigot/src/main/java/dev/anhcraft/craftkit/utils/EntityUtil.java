package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.CBEntityService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Utilities related to entity
 */
public class EntityUtil {
    private static final CBEntityService SERVICE = CBProvider.getService(CBEntityService.class).orElseThrow(UnsupportedOperationException::new);

    /**
     * Gets the bounding box of the given entity.
     * @param entity entity
     * @return {@link BoundingBox}
     */
    @NotNull
    public static BoundingBox getBoundingBox(@NotNull Entity entity){
        Condition.argNotNull("entity", entity);
        return SERVICE.getBoundingBox(entity);
    }

    /**
     * Overrides the current bounding box of the given entity.
     * @param entity entity
     * @param box the bounding box
     */
    public static void setBoundingBox(@NotNull Entity entity, @NotNull BoundingBox box){
        Condition.argNotNull("entity", entity);
        Condition.argNotNull("box", box);
        SERVICE.setBoundingBox(entity, box);
    }
}
