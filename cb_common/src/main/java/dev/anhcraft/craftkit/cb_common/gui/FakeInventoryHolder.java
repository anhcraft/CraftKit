package dev.anhcraft.craftkit.cb_common.gui;

import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

// TODO Stop support InventoryHolder from 1.13+
@Deprecated
public class FakeInventoryHolder implements InventoryHolder {
    private CustomGUI inventory;

    @Override
    public @NotNull CustomGUI getInventory() {
        return inventory;
    }

    public void setInventory(CustomGUI inventory) {
        this.inventory = inventory;
    }
}
