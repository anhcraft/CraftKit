package dev.anhcraft.craftkit.entity;

import dev.anhcraft.craftkit.cb_common.internal.services.CBEntityArmorStandService;
import dev.anhcraft.craftkit.cb_common.internal.services.ServiceProvider;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A fake armor stand!
 */
public class ArmorStand extends CustomEntity {
    private final CBEntityArmorStandService service = ServiceProvider.getService(CBEntityArmorStandService.class, false).orElseThrow(UnsupportedOperationException::new);

    /**
     * Spawns a new armor stand at the given location.
     * @param location the location
     * @return {@link ArmorStand}
     */
    @NotNull
    public static ArmorStand spawn(@NotNull Location location){
        Condition.argNotNull("location", location);
        return new ArmorStand(location);
    }

    private ArmorStand(Location location){
        service.init(location.getWorld(), location.getX(), location.getY(), location.getZ());
        id = service.getId();
        this.location = location.clone();
    }

    @Override
    protected void addViewerCallback(List<Player> players) {
        for(Player p : players){
            service.addViewer(p);
        }
    }

    @Override
    protected void removeViewerCallback(List<Player> players) {
        for(Player p : players){
            service.removeViewer(p);
        }
    }

    @Override
    protected void killCallback() {
        removeViewerCallback(viewers);
    }

    @Override
    protected void teleportCallback() {
        service.teleport(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    private EulerAngle angleFromNMS(float[] x){
        return new EulerAngle(Math.toRadians(x[0]), Math.toRadians(x[1]), Math.toRadians(x[2]));
    }

    private float[] angleToNMS(EulerAngle o){
        return new float[]{
                (float) Math.toDegrees(o.getX()),
                (float) Math.toDegrees(o.getY()),
                (float) Math.toDegrees(o.getZ())
        };
    }

    @NotNull
    public EulerAngle getBodyPose(){
        return angleFromNMS(service.getBodyPose());
    }

    public void setBodyPose(@NotNull EulerAngle angle){
        service.setBodyPose(angleToNMS(angle));
    }

    @NotNull
    public EulerAngle getLeftArmPose(){
        return angleFromNMS(service.getLeftArmPose());
    }

    public void setLeftArmPose(@NotNull EulerAngle angle){
        service.setLeftArmPose(angleToNMS(angle));
    }

    @NotNull
    public EulerAngle getRightArmPose(){
        return angleFromNMS(service.getRightArmPose());
    }

    public void setRightArmPose(@NotNull EulerAngle angle){
        service.setRightArmPose(angleToNMS(angle));
    }

    @NotNull
    public EulerAngle getLeftLegPose(){
        return angleFromNMS(service.getLeftLegPose());
    }

    public void setLeftLegPose(@NotNull EulerAngle angle){
        service.setLeftLegPose(angleToNMS(angle));
    }

    @NotNull
    public EulerAngle getRightLegPose(){
        return angleFromNMS(service.getRightLegPose());
    }

    public void setRightLegPose(@NotNull EulerAngle angle){
        service.setRightLegPose(angleToNMS(angle));
    }

    @NotNull
    public EulerAngle getHeadPose(){
        return angleFromNMS(service.getHeadPose());
    }

    public void setHeadPose(@NotNull EulerAngle angle){
        service.setHeadPose(angleToNMS(angle));
    }

    public boolean hasBasePlate(){
        return !service.hasBasePlate();
    }

    public void setBasePlate(boolean b){
        service.setBasePlate(!b);
    }

    public boolean hasArms(){
        return service.hasArms();
    }

    public void setArms(boolean b){
        service.setArms(b);
    }

    public boolean isSmall(){
        return service.isSmall();
    }

    public void setSmall(boolean b){
        service.setSmall(b);
    }

    public boolean isVisible(){
        return service.isVisible();
    }

    public void setVisible(boolean b){
        service.setVisible(b);
    }

    /**
     * Puts the given equipment to a specific slot.
     * @param slot the slot
     * @param itemStack the equipment
     */
    public void setEquipment(@NotNull EquipmentSlot slot, @NotNull ItemStack itemStack){
        Condition.argNotNull("slot", slot);
        Condition.argNotNull("itemStack", itemStack);
        service.setEquipment(slot, itemStack);
    }

    /**
     * Gets the equipment from the given slot.
     * @param slot the slot
     * @return the {@link ItemStack}
     */
    @NotNull
    public ItemStack getEquipment(@NotNull EquipmentSlot slot){
        Condition.argNotNull("slot", slot);
        return service.getEquipment(slot);
    }

    /**
     * Updates the looks of this armor stand on the client-side.<br>
     * This method should only be called when it is actually needed.
     * (Multiple changes to the armor stand only require one single call to this method)<br>
     * For example:
     * <pre>
     *      armorstand.setVisible(true);
     *      armorstand.setSmall(true);
     *      armorstand.setEquipment(EquipmentSlot.HEAD, new ItemStack(Material.STONE, 1));
     *      armorstand.sendUpdate(); // send updates to all viewers
     * </pre>
     * One extra thing to know is: It is <b>unnecessary</b> to call this method after:
     * <ul>
     *     <li>Adding or removing viewers</li>
     *     <li>Teleporting the armor stand</li>
     * </ul>
     */
    public void sendUpdate(){
        service.sendUpdate();
    }
}
