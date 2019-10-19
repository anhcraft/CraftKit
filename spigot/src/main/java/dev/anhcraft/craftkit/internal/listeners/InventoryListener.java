package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.cb_common.inventory.CustomInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent event){
        // use #getInventory instead of #getClickedInventory to ensure the inventory is top one
        if(event.getInventory() instanceof CustomInventory){
            ((CustomInventory) event.getInventory()).onClick(event);
        }
    }

    @EventHandler
    public void invClose(InventoryCloseEvent event){
        if(event.getInventory() instanceof CustomInventory){
            ((CustomInventory) event.getInventory()).onClose(event);
        }
    }

    @EventHandler
    public void invOpen(InventoryOpenEvent event){
        if(event.getInventory() instanceof CustomInventory){
            ((CustomInventory) event.getInventory()).onOpen(event);
        }
    }
}
