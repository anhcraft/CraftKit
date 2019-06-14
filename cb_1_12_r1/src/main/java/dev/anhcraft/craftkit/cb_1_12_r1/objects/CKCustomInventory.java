package dev.anhcraft.craftkit.cb_1_12_r1.objects;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.InventoryCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import dev.anhcraft.craftkit.cb_common.kits.inventory.CustomInventory;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CKCustomInventory extends CraftInventoryCustom implements CustomInventory {
    private final Multimap<Integer, SlotCallback> slots = HashMultimap.create();
    private final List<InventoryCallback> inv = new ArrayList<>();
    private boolean destroyed;

    public CKCustomInventory(InventoryHolder owner, int size, String title) {
        super(owner, size, title);
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void destroy() {
        destroyed = true;
        clear();
        HandlerList.unregisterAll(this);
        new ArrayList<>(getViewers()).forEach(HumanEntity::closeInventory);
    }

    @Override
    public void addInventoryCallback(InventoryCallback callback) {
        inv.add(callback);
    }

    @Override
    public void clearInventoryCallback() {
        inv.clear();
    }

    @Override
    public void addSlotCallback(int position, SlotCallback callback) {
        slots.put(position, callback);
    }

    @Override
    public void clearSlotCallback(int position) {
        slots.removeAll(position);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!Objects.equals(event.getClickedInventory(), this) || !(event.getWhoClicked() instanceof Player)) return;
        var c = slots.get(event.getRawSlot());
        if(c != null) {
            var p = (Player) event.getWhoClicked();
            for(var x : c) x.click(event, p, this);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if(!Objects.equals(event.getInventory(), this)) return;
        if(!(event.getPlayer() instanceof Player)) return;
        var p = (Player) event.getPlayer();
        for(var x : inv) x.open(event, p, this);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(!Objects.equals(event.getInventory(), this) || !(event.getPlayer() instanceof Player)) return;
        var p = (Player) event.getPlayer();
        for(var x : inv) x.close(event, p, this);
    }
}
