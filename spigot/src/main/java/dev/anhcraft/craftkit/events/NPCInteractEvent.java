package dev.anhcraft.craftkit.events;

import dev.anhcraft.craftkit.cb_common.kits.entity.NPC;
import dev.anhcraft.craftkit.lang.enumeration.HandSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event triggers when a player interacts with an NPC created by this library.
 */
// TODO Implements this to the listener
public class NPCInteractEvent extends Event {
    /**
     * Types of interact action.
     */
    public enum ActionType {
        /**
         * It is a normal interact by the right hand.
         */
        INTERACT,
        /**
         * It is an attack! (This is like an interact action but with the left hand)
         */
        ATTACK,

        //INTERACT_AT <- This one never happens
    }

    private static final HandlerList handlers = new HandlerList();
    private ActionType action;
    private HandSlot slot;
    private Player player;
    private NPC npc;

    public NPCInteractEvent(Player player, NPC npc, ActionType action, HandSlot slot){
        this.player = player;
        this.action = action;
        this.slot = slot;
        this.npc = npc;
    }

    /**
     * Returns the NPC who the player interacted at.
     * @return NPC
     */
    public NPC getNPC(){
        return this.npc;
    }

    /**
     * Returns the hand slot which is used to interact.
     * @return the hand slot
     */
    public HandSlot getSlot(){
        return this.slot;
    }

    /**
     * Returns the type of interact.
     * @return action type
     */
    public ActionType getActionType() {
        return action;
    }

    /**
     * Returns the player who did the action.
     * @return player
     */
    public Player getPlayer(){
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
