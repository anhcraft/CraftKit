package dev.anhcraft.craftkit.cb_1_16_r3.services;

import com.mojang.datafixers.util.Pair;
import dev.anhcraft.craftkit.cb_1_16_r3.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBEntityArmorStandBackend;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_16_R3.util.WeakCollection;
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
        as.setSlot(s, i);
    }

    @Override
    public org.bukkit.inventory.ItemStack getEquipment(EquipmentSlot slot) {
        return CraftItemStack.asBukkitCopy(as.getEquipment(CraftEquipmentSlot.getNMS(slot)));
    }

    @Override
    public void teleport(World world, double x, double y, double z, float yaw, float pitch) {
        as.world = ((CraftWorld) world).getHandle();
        as.setLocation(x, y, z, yaw, pitch);
        sendPacket(new PacketPlayOutEntityTeleport(as), viewers);
    }

    @Override
    public float[] getBodyPose() {
        return new float[]{
                as.bodyPose.getX(),
                as.bodyPose.getY(),
                as.bodyPose.getZ()
        };
    }

    @Override
    public void setBodyPose(float[] angles) {
        as.setBodyPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getLeftArmPose() {
        return new float[]{
                as.leftArmPose.getX(),
                as.leftArmPose.getY(),
                as.leftArmPose.getZ()
        };
    }

    @Override
    public void setLeftArmPose(float[] angles) {
        as.setLeftArmPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getRightArmPose() {
        return new float[]{
                as.rightArmPose.getX(),
                as.rightArmPose.getY(),
                as.rightArmPose.getZ()
        };
    }

    @Override
    public void setRightArmPose(float[] angles) {
        as.setRightArmPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getLeftLegPose() {
        return new float[]{
                as.leftLegPose.getX(),
                as.leftLegPose.getY(),
                as.leftLegPose.getZ()
        };
    }

    @Override
    public void setLeftLegPose(float[] angles) {
        as.setLeftLegPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getRightLegPose() {
        return new float[]{
                as.rightLegPose.getX(),
                as.rightLegPose.getY(),
                as.rightLegPose.getZ()
        };
    }

    @Override
    public void setRightLegPose(float[] angles) {
        as.setRightLegPose(new Vector3f(angles[0], angles[1], angles[2]));
    }

    @Override
    public float[] getHeadPose() {
        return new float[]{
                as.headPose.getX(),
                as.headPose.getY(),
                as.headPose.getZ()
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
