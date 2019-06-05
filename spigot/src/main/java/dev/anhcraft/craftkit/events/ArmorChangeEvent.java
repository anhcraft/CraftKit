package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * This event triggers when a player changes his armor (e.g: swap two armors in a hand and in an inventory slot).
 */
public class ArmorChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack oldArmor;
    private ItemStack newArmor;
    private EquipmentSlot slot;
    private Player player;

    public ArmorChangeEvent(Player player, ItemStack oldArmor, ItemStack newArmor, EquipmentSlot slot){
        this.player = player;
        this.oldArmor = oldArmor;
        this.newArmor = newArmor;
        this.slot = slot;
    }

    /**
     * Returns the slot (both armors have a same slot).
     * @return slot
     */
    public EquipmentSlot getSlot(){
        return this.slot;
    }

    /**
     * Returns the player who changed his armor.
     * @return player
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Returns the new armor.
     * @return armor
     */
    public ItemStack getNewArmor(){
        return this.newArmor;
    }

    /**
     * Returns the old armor.
     * @return armor
     */
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