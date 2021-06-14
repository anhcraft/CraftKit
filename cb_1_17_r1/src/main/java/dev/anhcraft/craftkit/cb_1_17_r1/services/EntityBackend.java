package dev.anhcraft.craftkit.cb_1_17_r1.services;

import com.mojang.datafixers.util.Pair;
import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBEntityBackend;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;
import net.minecraft.server.*;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;

public class EntityBackend extends CBModule implements CBEntityBackend {
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
        ent.setYRot(yaw % 360.0f);
        ent.setXRot(pitch % 360.0f);

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
        net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        ent.setSlot(nmsSlot, item);
    }

    @Override
    public void displayItem(int entity, EquipmentSlot slot, ItemStack itemStack, Collection<Object> viewers) {
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entity, Collections.singletonList(Pair.of(CraftEquipmentSlot.getNMS(slot), CraftItemStack.asNMSCopy(itemStack))));
        for(EntityPlayer player : castEntityPlayers(viewers)){
            player.b.a.sendPacket(packet);
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
