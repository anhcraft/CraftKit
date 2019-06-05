package dev.anhcraft.craftkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * This event triggers when a player equips an armor.
 */
public class ArmorEquipEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack armor;
    private EquipmentSlot slot;
    private Player player;

    public ArmorEquipEvent(Player player, ItemStack armor, EquipmentSlot slot){
        this.player = player;
        this.armor = armor;
        this.slot = slot;
    }

    /**
     * Returns the slot which the armor was put into.
     * @return slot
     */
    public EquipmentSlot getSlot(){
        return this.slot;
    }

    /**
     * Returns the player who equipped the armor.
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