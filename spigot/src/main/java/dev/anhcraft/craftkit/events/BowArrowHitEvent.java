package dev.anhcraft.craftkit.events;

import dev.anhcraft.jvmkit.utils.Pair;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * This event triggers when an arrow (which was shot by a living entity with its bow) hit something.
 */
public class BowArrowHitEvent extends Event {
    public static final HandlerList handlers = new HandlerList();
    private Arrow arrow;
    private LivingEntity shooter;
    private ItemStack bow;

    public BowArrowHitEvent(Arrow arrow, Pair<LivingEntity, ItemStack> x) {
        this.arrow = arrow;
        this.shooter = x.getFirst();
        this.bow = x.getSecond();
    }

    /**
     * Returns the arrow which was shot.
     * @return arrow
     */
    public Arrow getArrow() {
        return arrow;
    }

    /**
     * Returns the entity who shot the arrow.
     * @return shooter
     */
    public LivingEntity getShooter() {
        return shooter;
    }

    /**
     * Returns the bow which was used to shoot.
     * @return bow
     */
    public ItemStack getBow() {
        return bow;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}