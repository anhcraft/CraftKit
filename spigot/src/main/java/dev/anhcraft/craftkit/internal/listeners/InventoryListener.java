package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.FakeInventoryHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent event){
        // use #getInventory instead of #getClickedInventory to ensure the inventory is top one
        Inventory inv = event.getInventory();
        if(inv instanceof BaseGUI){
            ((BaseGUI) inv).onClick(event);
        } else if(inv.getHolder() instanceof FakeInventoryHolder){
            ((FakeInventoryHolder) inv.getHolder()).getInventory().onClick(event);
        }
    }

    @EventHandler
    public void invClose(InventoryCloseEvent event){
        Inventory inv = event.getInventory();
        if(inv instanceof BaseGUI){
            ((BaseGUI) inv).onClose(event);
        } else if(inv.getHolder() instanceof FakeInventoryHolder){
            ((FakeInventoryHolder) inv.getHolder()).getInventory().onClose(event);
        }
    }

    @EventHandler
    public void invOpen(InventoryOpenEvent event){
        Inventory inv = event.getInventory();
        if(inv instanceof BaseGUI){
            ((BaseGUI) inv).onOpen(event);
        } else if(inv.getHolder() instanceof FakeInventoryHolder){
            ((FakeInventoryHolder) inv.getHolder()).getInventory().onOpen(event);
        }
    }
}
