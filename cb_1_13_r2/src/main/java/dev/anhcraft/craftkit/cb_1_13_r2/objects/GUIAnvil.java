package dev.anhcraft.craftkit.cb_1_13_r2.objects;

import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.AnvilGUI;
import dev.anhcraft.craftkit.cb_common.internal.GuiMiddleware;
import net.minecraft.server.v1_13_R2.ContainerAnvil;
import net.minecraft.server.v1_13_R2.IInventory;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftInventoryAnvil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

public class GUIAnvil extends CraftInventoryAnvil implements AnvilGUI {
    private GuiMiddleware inv = new GuiMiddleware();

    public GUIAnvil(Location location, IInventory inventory, IInventory resultInventory, ContainerAnvil container) {
        super(location, inventory, resultInventory, container);
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
