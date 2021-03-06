package dev.anhcraft.craftkit.cb_1_9_r2.services;

import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.internal.GuiMiddleware;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBCustomGUIBackend;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CustomGUIBackend extends CraftInventoryCustom implements CBCustomGUIBackend {
    private final GuiMiddleware inv = new GuiMiddleware();

    public CustomGUIBackend(InventoryHolder owner, int size, String title) {
        super(owner, size, title);
    }

    @Override
    public void addInterfaceCallback(@NotNull GuiCallback callback) {
        inv.addInterfaceCallback(callback);
    }

    @Override
    public void clearInterfaceCallbacks() {
        inv.clearInterfaceCallbacks();
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
