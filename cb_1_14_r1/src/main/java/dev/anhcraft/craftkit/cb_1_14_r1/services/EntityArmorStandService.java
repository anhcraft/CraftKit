package dev.anhcraft.craftkit.cb_1_14_r1.services;

import dev.anhcraft.craftkit.cb_1_14_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBEntityArmorStandService;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_14_R1.util.WeakCollection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

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
            for(EnumItemSlot s : EnumItemSlot.values()){
                sendPacket(new PacketPlayOutEntityEquipment(as.getId(), s, as.getEquipment(s)), p);
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
    public void setEquipment(EquipmentSlot slot, ItemStack itemStack) {
        net.minecraft.server.v1_14_R1.ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        EnumItemSlot s = CraftEquipmentSlot.getNMS(slot);
        as.setEquipment(s, i);
        sendPacket(new PacketPlayOutEntityEquipment(as.getId(), s, i), viewers);
    }

    @Override
    public ItemStack getEquipment(EquipmentSlot slot) {
        return CraftItemStack.asBukkitCopy(as.getEquipment(CraftEquipmentSlot.getNMS(slot)));
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
    public boolean hasGravity() {
        return !as.isNoGravity();
    }

    @Override
    public void setGravity(boolean b) {
        as.setNoGravity(!b);
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
    public boolean isMarker() {
        return as.isMarker();
    }

    @Override
    public void setMarker(boolean b) {
        as.setMarker(b);
    }

    @Override
    public Object getEntity() {
        return as;
    }
}
