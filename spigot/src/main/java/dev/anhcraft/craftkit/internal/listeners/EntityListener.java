package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.internal.assistants.CKCleaner;
import dev.anhcraft.craftkit.common.lang.annotation.RequiredCleaner;
import dev.anhcraft.craftkit.events.BowArrowHitEvent;
import org.apache.commons.lang3.tuple.Pair;
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
    @RequiredCleaner
    private static final Map<Entity, Pair<LivingEntity, ItemStack>> bowArrows = new HashMap<>();

    @EventHandler
    public void death(EntityDeathEvent event){
        CKProvider.TASK_HELPER.newAsyncTask(() -> CKCleaner.clean(o -> o.equals(event.getEntity())));
    }

    @EventHandler
    public void shoot(EntityShootBowEvent e){
        bowArrows.put(e.getProjectile(), Pair.of(e.getEntity(), e.getBow()));
    }

    @EventHandler
    public void hit(ProjectileHitEvent e){
        if(bowArrows.containsKey(e.getEntity()) && e.getEntity() instanceof Arrow){
            BowArrowHitEvent ev = new BowArrowHitEvent((Arrow) e.getEntity(), bowArrows.get(e.getEntity()));
            Bukkit.getPluginManager().callEvent(ev);
            bowArrows.remove(e.getEntity());
        }
    }
}
