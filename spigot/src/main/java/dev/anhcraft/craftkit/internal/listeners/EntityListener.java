package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.events.BowArrowHitEvent;
import dev.anhcraft.jvmkit.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EntityListener implements Listener {
    private final Map<Entity, Pair<LivingEntity, ItemStack>> bowArrows = new HashMap<>();

    @EventHandler
    public void death(EntityDeathEvent event){
        bowArrows.remove(event.getEntity());
    }

    @EventHandler
    public void shoot(EntityShootBowEvent e){
        bowArrows.put(e.getProjectile(), new Pair<>(e.getEntity(), e.getBow()));
    }

    @EventHandler
    public void hit(ProjectileHitEvent e){
        if(e.getEntity() instanceof Arrow) {
            Pair<LivingEntity, ItemStack> pair = bowArrows.get(e.getEntity());
            if (pair != null) {
                BowArrowHitEvent ev = new BowArrowHitEvent((Arrow) e.getEntity(), pair);
                Bukkit.getPluginManager().callEvent(ev);
                bowArrows.remove(e.getEntity());
            }
        }
    }
}
