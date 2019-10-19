package dev.anhcraft.craftkit.cb_1_9_r2.services;

import dev.anhcraft.craftkit.cb_common.callbacks.inventory.InventoryCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import dev.anhcraft.craftkit.cb_common.internal.CBCustomInventoryService;
import dev.anhcraft.craftkit.cb_common.internal.objects.InventoryMiddleware;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CustomInventoryService extends CraftInventoryCustom implements CBCustomInventoryService {
    private InventoryMiddleware inv = new InventoryMiddleware();

    public CustomInventoryService(InventoryHolder owner, int size, String title) {
        super(owner, size, title);
    }

    @Override
    public void addInventoryCallback(@NotNull InventoryCallback callback) {
        inv.addInventoryCallback(callback);
    }

    @Override
    public void clearInventoryCallbacks() {
        inv.clearInventoryCallbacks();
    }

    @Override
    public void addSlotCallback(int position, @NotNull SlotCallback callback) {
        inv.addSlotCallback(position, callback);
    }

    @Override
    public void clearSlotCallbacks(int position) {
        inv.clearSlotCallbacks(position);
    }

    @Override
    public void onClick(@NotNull InventoryClickEvent event) {
        inv.onClick(event, this);
    }

    @Override
    public void onOpen(@NotNull InventoryOpenEvent event) {
        inv.onOpen(event, this);
    }

    @Override
    public void onClose(@NotNull InventoryCloseEvent event) {
        inv.onClose(event, this);
    }
}
