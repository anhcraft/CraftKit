package dev.anhcraft.craftkit.cb_1_13_r1.services;

import dev.anhcraft.craftkit.cb_1_13_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.services.CBEntityService;
import net.minecraft.server.v1_13_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class EntityService extends CBModule implements CBEntityService {
    @Override
    public Object toNms(org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    @Override
    public int getId(Object entity) {
        return ((Entity) entity).getId();
    }

    @Override
    public void rotate(Object entity, float yaw, float pitch, Collection<Object> viewers) {
        Entity ent = (Entity) entity;
        ent.lastYaw = ent.yaw;
        ent.yaw = yaw % 360.0f;
        ent.lastPitch = ent.pitch;
        ent.pitch = pitch % 360.0f;

        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(ent.getId(), (byte) yaw, (byte) pitch, false);
        sendPacket(packet, castEntityPlayers(viewers));
    }

    @Override
    public void teleport(Object entity, Location location, Collection<Object> viewers) {
        Entity ent = (Entity) entity;
        ent.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(ent);
        sendPacket(packet, castEntityPlayers(viewers));
    }

    @Override
    public void setItem(Object entity, EquipmentSlot slot, ItemStack itemStack) {
        EntityLiving ent = (EntityLiving) entity;
        EnumItemSlot nmsSlot = CraftEquipmentSlot.getNMS(slot);
        net.minecraft.server.v1_13_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        ent.setSlot(nmsSlot, item);
    }

    @Override
    public void displayItem(int entity, EquipmentSlot slot, ItemStack itemStack, Collection<Object> viewers) {
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entity, CraftEquipmentSlot.getNMS(slot), CraftItemStack.asNMSCopy(itemStack));
        for(EntityPlayer player : castEntityPlayers(viewers)){
            player.playerConnection.networkManager.sendPacket(packet);
        }
    }

    @Override
    public ItemStack getItem(Object entity, EquipmentSlot slot) {
        EntityLiving ent = (EntityLiving) entity;
        return CraftItemStack.asBukkitCopy(ent.getEquipment(CraftEquipmentSlot.getNMS(slot)));
    }

    @Override
    public BoundingBox getBoundingBox(org.bukkit.entity.Entity entity) {
        CraftEntity craftEntity = (CraftEntity) entity;
        AxisAlignedBB aabb = craftEntity.getHandle().getBoundingBox();
        return new BoundingBox(aabb.a, aabb.b, aabb.c, aabb.d, aabb.e, aabb.f);
    }

    @Override
    public void setBoundingBox(org.bukkit.entity.Entity entity, BoundingBox box) {
        CraftEntity craftEntity = (CraftEntity) entity;
        craftEntity.getHandle().a(new AxisAlignedBB(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()));
    }
}
