package dev.anhcraft.craftkit.cb_common.internal;

import dev.anhcraft.craftkit.cb_common.inventory.CustomInventory;
import org.bukkit.inventory.InventoryHolder;

public interface CBCustomInventoryService extends CBService {
    CustomInventory create(InventoryHolder holder, int size, String title);
}
