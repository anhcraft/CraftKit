package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.services.CBEntityService;
import dev.anhcraft.craftkit.cb_common.internal.services.ServiceProvider;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Utilities related to entity
 */
public class EntityUtil {
    private static final CBEntityService SERVICE = ServiceProvider.getService(CBEntityService.class).orElseThrow(UnsupportedOperationException::new);

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

    /**
     * Displays fake equipment over the body of an entity
     * @param id the entity's id
     * @param slot the slot
     * @param itemStack stack of items
     * @param viewer who can view the fake equipment
     */
    public static void displayEquipment(int id, @NotNull EquipmentSlot slot, ItemStack itemStack, @NotNull Player viewer){
        Condition.argNotNull("slot", slot);
        Condition.argNotNull("itemStack", itemStack);
        Condition.argNotNull("viewer", viewer);
        SERVICE.displayItem(id, slot, itemStack, Collections.singletonList(SERVICE.toNms(viewer)));
    }

    /**
     * Displays fake equipment over the body of an entity
     * @param id the entity's id
     * @param slot the slot
     * @param itemStack stack of items
     * @param viewers who can view the fake equipment
     */
    public static void displayEquipment(int id, @NotNull EquipmentSlot slot, ItemStack itemStack, @NotNull Collection<Player> viewers){
        Condition.argNotNull("slot", slot);
        Condition.argNotNull("itemStack", itemStack);
        Condition.argNotNull("viewers", viewers);
        SERVICE.displayItem(id, slot, itemStack, viewers.stream().map(SERVICE::toNms).collect(Collectors.toList()));
    }
}
