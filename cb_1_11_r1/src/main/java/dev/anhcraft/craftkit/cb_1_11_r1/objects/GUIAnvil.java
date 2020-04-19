package dev.anhcraft.craftkit.cb_1_11_r1.objects;

import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.AnvilGUI;
import dev.anhcraft.craftkit.cb_common.internal.GuiMiddleware;
import net.minecraft.server.v1_11_R1.ContainerAnvil;
import net.minecraft.server.v1_11_R1.IInventory;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftInventoryAnvil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

public class GUIAnvil extends CraftInventoryAnvil implements AnvilGUI {
    private final GuiMiddleware inv = new GuiMiddleware();
    private final ContainerAnvil container;

    public GUIAnvil(Location location, IInventory inventory, IInventory resultInventory, ContainerAnvil container) {
        super(location, inventory, resultInventory, container);
        this.container = container;
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

    @Override
    @NotNull
    public String getInputText() {
        return container.l;
    }

    @Override
    public void setInputText(String text) {
        container.l = (text == null ? "" : text);
    }
}
