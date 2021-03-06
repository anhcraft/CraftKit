package dev.anhcraft.craftkit.cb_1_17_r1.services;

import com.mojang.datafixers.util.Pair;
import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBEntityArmorStandBackend;
import net.minecraft.core.Vector3f;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.item.ItemStack;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_17_R1.util.WeakCollection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class EntityArmorStandBackend extends CBModule implements CBEntityArmorStandBackend {
    private final Object viewerLock = new Object();
    private final WeakCollection<EntityPlayer> viewers = new WeakCollection<>();
    private EntityArmorStand as;

    @Override
    public void init(org.bukkit.World world, double x, double y, double z) {
        as = new EntityArmorStand(((CraftWorld) world).getHandle(), x, y, z);
    }

    @Override
    public int getId() {
        return as.getId();
    }

    @Override
    public void addViewer(Player player) {
        synchronized (viewerLock) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            viewers.add(p);
            sendPacket(new PacketPlayOutSpawnEntityLiving(as), p);
            List<Pair<EnumItemSlot, ItemStack>> list = new ArrayList<>();
            for(EnumItemSlot slot : EnumItemSlot.values()) {
                list.add(Pair.of(slot, as.getEquipment(slot)));
            }
            sendPacket(new PacketPlayOutEntityEquipment(as.getId(), list), p);
            sendPacket(new PacketPlayOutEntityMetadata(as.getId(), as.getDataWatcher(), true), p);
        }
    }

    @Override
    public void removeViewer(Player player) {
        synchronized (viewerLock) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            viewers.remove(p);
            sendPacket(new PacketPlayOutEntityDestroy(as.getId()), p);
        }
    }

    @Override
    public void setName(String s) {
        as.setCustomName(CraftChatMessage.fromString(s, true)[0]);
    }

    @Override
    public String getName() {
        return CraftChatMessage.fromComponent(as.getCustomName());
    }

    @Override
    public boolean isNameVisible() {
        return as.getCustomNameVisible();
    }

    @Override
    public void setNameVisible(boolean b) {
        as.setCustomNameVisible(b);
    }

    @Override
    public void setEquipment(EquipmentSlot slot, org.bukkit.inventory.ItemStack itemStack) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        EnumItemSlot s = CraftEquipmentSlot.getNMS(slot);
        as.setSlot(s, i, true);
    }

    @Override
    public org.bukkit.inventory.ItemStack getEquipment(EquipmentSlot slot) {
        return CraftItemStack.asBukkitCopy(as.getEquipment(CraftEquipmentSlot.getNMS(slot)));
    }

    @Override
    public void teleport(World world, double x, double y, double z, float yaw, float pitch) {
        as.t = ((CraftWorld) world).getHandle();
        as.setLocation(x, y, z, yaw, pitch);
        sendPacket(new PacketPlayOutEntityTeleport(as), viewers);
    }

    @Override
    public float[] getBodyPose() {
        return new float[]{
                as.ch.getX(),
                as.ch.getY(),
                as.ch.getZ()
        };
    }

    @Override
    public void setBodyPose(float[] angles) {
        as.setBodyPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getLeftArmPose() {
        return new float[]{
                as.ci.getX(),
                as.ci.getY(),
                as.ci.getZ()
        };
    }

    @Override
    public void setLeftArmPose(float[] angles) {
        as.setLeftArmPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getRightArmPose() {
        return new float[]{
                as.cj.getX(),
                as.cj.getY(),
                as.cj.getZ()
        };
    }

    @Override
    public void setRightArmPose(float[] angles) {
        as.setRightArmPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getLeftLegPose() {
        return new float[]{
                as.ck.getX(),
                as.ck.getY(),
                as.ck.getZ()
        };
    }

    @Override
    public void setLeftLegPose(float[] angles) {
        as.setLeftLegPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getRightLegPose() {
        return new float[]{
                as.cl.getX(),
                as.cl.getY(),
                as.cl.getZ()
        };
    }

    @Override
    public void setRightLegPose(float[] angles) {
        as.setRightLegPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getHeadPose() {
        return new float[]{
                as.cg.getX(),
                as.cg.getY(),
                as.cg.getZ()
        };
    }

    @Override
    public void setHeadPose(float[] angles) {
        as.setHeadPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public boolean hasBasePlate() {
        return as.hasBasePlate();
    }

    @Override
    public void setBasePlate(boolean b) {
        as.setBasePlate(b);
    }

    @Override
    public boolean hasArms() {
        return as.hasArms();
    }

    @Override
    public void setArms(boolean b) {
        as.setArms(b);
    }

    @Override
    public boolean isSmall() {
        return as.isSmall();
    }

    @Override
    public void setSmall(boolean b) {
        as.setSmall(b);
    }

    @Override
    public boolean isVisible() {
        return !as.isInvisible();
    }

    @Override
    public void setVisible(boolean b) {
        as.setInvisible(!b);
    }

    @Override
    public Object getEntity() {
        return as;
    }

    @Override
    public void sendUpdate() {
        List<Pair<EnumItemSlot, ItemStack>> list = new ArrayList<>();
        for(EnumItemSlot slot : EnumItemSlot.values()) {
            list.add(Pair.of(slot, as.getEquipment(slot)));
        }
        sendPacket(new PacketPlayOutEntityEquipment(as.getId(), list), viewers);
        sendPacket(new PacketPlayOutEntityMetadata(as.getId(), as.getDataWatcher(), true), viewers);
    }
}
