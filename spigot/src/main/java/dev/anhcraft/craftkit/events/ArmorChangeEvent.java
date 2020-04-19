package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This event triggers when a player changes his armor (e.g: swap two armors in a hand and in an inventory slot).
 */
public class ArmorChangeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack oldArmor;
    private final ItemStack newArmor;
    private final EquipmentSlot slot;

    public ArmorChangeEvent(@NotNull Player player, @NotNull ItemStack oldArmor, @NotNull ItemStack newArmor, @NotNull EquipmentSlot slot){
        super(player);
        this.oldArmor = oldArmor;
        this.newArmor = newArmor;
        this.slot = slot;
    }

    /**
     * Returns the slot (both armors have a same slot).
     * @return slot
     */
    @NotNull
    public EquipmentSlot getSlot(){
        return this.slot;
    }

    /**
     * Returns the new armor.
     * @return armor
     */
    @NotNull
    public ItemStack getNewArmor(){
        return this.newArmor;
    }

    /**
     * Returns the old armor.
     * @return armor
     */
    @NotNull
    public ItemStack getOldArmor(){
        return this.oldArmor;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}