package dev.anhcraft.craftkit.cb_common.internal.services;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.annotation.IsNMS;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CBEntityService extends CBService {
    Object toNms(Entity entity);
    int getId(@IsNMS Object entity);
    void rotate(@IsNMS Object entity, float yaw, float pitch, @IsNMS List<Object> viewers);
    void teleport(@IsNMS Object entity, Location location, @IsNMS List<Object> viewers);
    void setItem(@IsNMS Object entity, EquipmentSlot slot, ItemStack itemStack);
    void displayItem(@IsNMS int entity, EquipmentSlot slot, ItemStack itemStack, @IsNMS List<Object> viewers);
    ItemStack getItem(@IsNMS Object entity, EquipmentSlot slot);
    BoundingBox getBoundingBox(Entity entity);
    void setBoundingBox(Entity entity, BoundingBox box);
}
