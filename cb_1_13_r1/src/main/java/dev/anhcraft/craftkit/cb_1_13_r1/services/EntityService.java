package dev.anhcraft.craftkit.cb_1_13_r1.services;

import dev.anhcraft.craftkit.cb_1_13_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.CBEntityService;
import dev.anhcraft.jvmkit.utils.ReflectionUtil;
import net.minecraft.server.v1_13_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

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
    public void rotate(Object entity, float yaw, float pitch, List<Object> viewers) {
        Entity ent = (Entity) entity;
        ent.lastYaw = ent.yaw;
        ent.yaw = yaw % 360.0f;
        ent.lastPitch = ent.pitch;
        ent.pitch = pitch % 360.0f;

        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(ent.getId(), (byte) yaw, (byte) pitch, false);
        sendPacket(packet, castEntityPlayers(viewers));
    }

    @Override
    public void teleport(Object entity, Location location, List<Object> viewers) {
        Entity ent = (Entity) entity;
        ent.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(ent);
        sendPacket(packet, castEntityPlayers(viewers));
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

    @Override
    public void jump(LivingEntity entity) {
        CraftLivingEntity craftLivingEntity = (CraftLivingEntity) entity;
        EntityLiving entityLiving = craftLivingEntity.getHandle();
        ReflectionUtil.invokeDeclaredMethod(EntityLiving.class, entityLiving, "cH");
    }
}
