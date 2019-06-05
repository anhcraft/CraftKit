package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * This event triggers when a player unequips an armor.
 */
public class ArmorUnequipEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack armor;
    private EquipmentSlot slot;
    private Player player;

    public ArmorUnequipEvent(Player player, ItemStack armor, EquipmentSlot slot){
        this.player = player;
        this.armor = armor;
        this.slot = slot;
    }

    /**
     * Returns the slot which contained the armor.
     * @return slot
     */
    public EquipmentSlot getSlot(){
        return this.slot;
    }

    /**
     * Returns the player who unequipped the armor.
     * @return player
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Returns the armor.
     * @return armor
     */
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