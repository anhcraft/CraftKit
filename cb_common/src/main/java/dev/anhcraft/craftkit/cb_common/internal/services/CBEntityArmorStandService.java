package dev.anhcraft.craftkit.cb_common.internal.services;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public interface CBEntityArmorStandService extends CBService {
    void init(World world, double x, double y, double z);
    int getId();
    void addViewer(Player player);
    void removeViewer(Player player);
    void setEquipment(EquipmentSlot slot, ItemStack itemStack);
    ItemStack getEquipment(EquipmentSlot slot);
    void teleport(World world, double x, double y, double z, float yaw, float pitch);
    float[] getBodyPose();
    void setBodyPose(float[] angles);
    float[] getLeftArmPose();
    void setLeftArmPose(float[] angles);
    float[] getRightArmPose();
    void setRightArmPose(float[] angles);
    float[] getLeftLegPose();
    void setLeftLegPose(float[] angles);
    float[] getRightLegPose();
    void setRightLegPose(float[] angles);
    float[] getHeadPose();
    void setHeadPose(float[] angles);
    boolean hasBasePlate();
    void setBasePlate(boolean b);
    boolean hasArms();
    void setArms(boolean b);
    boolean isSmall();
    void setSmall(boolean b);
    boolean isVisible();
    void setVisible(boolean b);
    Object getEntity();
    void sendUpdate();
}
