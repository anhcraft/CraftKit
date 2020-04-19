package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This event triggers when a player unequips an armor.
 */
public class ArmorUnequipEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack armor;
    private final EquipmentSlot slot;

    public ArmorUnequipEvent(@NotNull Player player, @NotNull ItemStack armor, @NotNull EquipmentSlot slot){
        super(player);
        this.armor = armor;
        this.slot = slot;
    }

    /**
     * Returns the slot which contained the armor.
     * @return slot
     */
    @NotNull
    public EquipmentSlot getSlot(){
        return this.slot;
    }

    /**
     * Returns the armor.
     * @return armor
     */
    @NotNull
    public ItemStack getArmor(){
        return this.armor;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}