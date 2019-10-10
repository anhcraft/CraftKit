package dev.anhcraft.craftkit.cb_1_11_r1.services;

import dev.anhcraft.craftkit.cb_1_11_r1.objects.CKCustomInventory;
import dev.anhcraft.craftkit.cb_common.internal.CBCustomInventoryService;
import dev.anhcraft.craftkit.cb_common.inventory.CustomInventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomInventoryService implements CBCustomInventoryService {
    @Override
    public CustomInventory create(InventoryHolder holder, int size, String title) {
        return new CKCustomInventory(holder, size, title);
    }
}
