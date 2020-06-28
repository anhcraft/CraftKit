package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.events.BowArrowHitEvent;
import dev.anhcraft.jvmkit.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityListener implements Listener {
    private final Map<UUID, Pair<LivingEntity, ItemStack>> bowArrows = new HashMap<>();

    @EventHandler
    public void death(EntityDeathEvent event){
        bowArrows.remove(event.getEntity().getUniqueId());
    }

    @EventHandler
    public void shoot(EntityShootBowEvent e){
        bowArrows.put(e.getProjectile().getUniqueId(), new Pair<>(e.getEntity(), e.getBow()));
    }

    @EventHandler
    public void hit(ProjectileHitEvent e){
        Pair<LivingEntity, ItemStack> pair = bowArrows.remove(e.getEntity().getUniqueId());
        if (pair != null && e.getEntity() instanceof Arrow) {
            BowArrowHitEvent ev = new BowArrowHitEvent((Arrow) e.getEntity(), pair);
            Bukkit.getPluginManager().callEvent(ev);
        }
    }
}
