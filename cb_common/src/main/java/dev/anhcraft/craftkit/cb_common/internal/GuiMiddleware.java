package dev.anhcraft.craftkit.cb_common.internal;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.BaseUI;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuiMiddleware implements BaseUI<GuiCallback, SlotCallback> {
    private final Multimap<Integer, SlotCallback> slotCallbacks = Multimaps.synchronizedMultimap(HashMultimap.create());
    private final List<GuiCallback> invCallbacks = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void addInterfaceCallback(@NotNull GuiCallback callback) {
        Condition.argNotNull("callback", callback);
        invCallbacks.add(callback);
    }

    @Override
    public void clearInterfaceCallbacks() {
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

    public void onClick(InventoryClickEvent event, BaseGUI inv) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Collection<SlotCallback> c = slotCallbacks.get(event.getRawSlot());
        if(c != null) {
            Player p = (Player) event.getWhoClicked();
            for(SlotCallback x : c) x.click(event, p, inv);
        }
    }

    public void onOpen(InventoryOpenEvent event, BaseGUI inv) {
        if(!(event.getPlayer() instanceof Player)) return;
        Player p = (Player) event.getPlayer();
        for(GuiCallback x : invCallbacks) x.open(event, p, inv);
    }

    public void onClose(InventoryCloseEvent event, BaseGUI inv) {
        if(!(event.getPlayer() instanceof Player)) return;
        Player p = (Player) event.getPlayer();
        for(GuiCallback x : invCallbacks) x.close(event, p, inv);
    }
}
