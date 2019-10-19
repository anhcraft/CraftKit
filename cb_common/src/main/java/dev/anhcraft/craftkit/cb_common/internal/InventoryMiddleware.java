package dev.anhcraft.craftkit.cb_common.internal;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.InventoryCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import dev.anhcraft.craftkit.cb_common.inventory.BaseInventory;
import dev.anhcraft.craftkit.cb_common.inventory.CustomInventory;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InventoryMiddleware implements BaseInventory {
    private final Multimap<Integer, SlotCallback> slotCallbacks = HashMultimap.create();
    private final List<InventoryCallback> invCallbacks = new ArrayList<>();

    @Override
    public void addInventoryCallback(@NotNull InventoryCallback callback) {
        Condition.argNotNull("callback", callback);
        invCallbacks.add(callback);
    }

    @Override
    public void clearInventoryCallbacks() {
        invCallbacks.clear();
    }

    @Override
    public void addSlotCallback(int position, @NotNull SlotCallback callback) {
        Condition.argNotNull("callback", callback);
        slotCallbacks.put(position, callback);
    }

    @Override
    public void clearSlotCallbacks(int position) {
        slotCallbacks.removeAll(position);
    }

    public void onClick(InventoryClickEvent event, CustomInventory inv) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Collection<SlotCallback> c = slotCallbacks.get(event.getRawSlot());
        if(c != null) {
            Player p = (Player) event.getWhoClicked();
            for(SlotCallback x : c) x.click(event, p, inv);
        }
    }

    public void onOpen(InventoryOpenEvent event, CustomInventory inv) {
        if(!(event.getPlayer() instanceof Player)) return;
        Player p = (Player) event.getPlayer();
        for(InventoryCallback x : invCallbacks) x.open(event, p, inv);
    }

    public void onClose(InventoryCloseEvent event, CustomInventory inv) {
        if(!(event.getPlayer() instanceof Player)) return;
        Player p = (Player) event.getPlayer();
        for(InventoryCallback x : invCallbacks) x.close(event, p, inv);
    }
}
