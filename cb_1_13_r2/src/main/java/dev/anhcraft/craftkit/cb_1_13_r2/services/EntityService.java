package dev.anhcraft.craftkit.cb_1_13_r2.services;

import dev.anhcraft.craftkit.cb_1_13_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBEntityService;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntity;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;

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
        var ent = (Entity) entity;
        ent.lastYaw = ent.yaw;
        ent.yaw = yaw % 360.0f;
        ent.lastPitch = ent.pitch;
        ent.pitch = pitch % 360.0f;

        var packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(ent.getId(), (byte) yaw, (byte) pitch, false);
        sendPacket(packet, castEntityPlayers(viewers));
    }

    @Override
    public void teleport(Object entity, Location location, List<Object> viewers) {
        var ent = (Entity) entity;
        ent.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        var packet = new PacketPlayOutEntityTeleport(ent);
        sendPacket(packet, castEntityPlayers(viewers));
    }
}
