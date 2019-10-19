package dev.anhcraft.craftkit.cb_1_13_r1.services;

import dev.anhcraft.craftkit.cb_1_13_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.services.CBEntityArmorStandService;
import net.minecraft.server.v1_13_R1.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_13_R1.util.WeakCollection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class EntityArmorStandService extends CBModule implements CBEntityArmorStandService {
    private final Object viewerLock = new Object();
    private final WeakCollection<EntityPlayer> viewers = new WeakCollection<>();
    private EntityArmorStand as;

    @Override
    public void init(World world, double x, double y, double z) {
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
            for(EnumItemSlot slot : EnumItemSlot.values()) {
                sendPacket(new PacketPlayOutEntityEquipment(as.getId(), slot, as.getEquipment(slot)), p);
            }
            sendPacket(new PacketPlayOutEntityMetadata(as.getId(), as.getDataWatcher(), false), p);
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
        for(EntityPlayer ep : viewers){
            for(EnumItemSlot slot : EnumItemSlot.values()) {
                sendPacket(new PacketPlayOutEntityEquipment(as.getId(), slot, as.getEquipment(slot)), ep);
            }
            sendPacket(new PacketPlayOutEntityMetadata(as.getId(), as.getDataWatcher(), false), ep);
        }
    }
}
