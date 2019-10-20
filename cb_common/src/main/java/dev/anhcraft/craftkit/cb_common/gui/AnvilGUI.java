package dev.anhcraft.craftkit.cb_common.gui;

import org.bukkit.inventory.AnvilInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an anvil GUI.
 */
public interface AnvilGUI extends BaseGUI, AnvilInventory {
    @NotNull
    String getInputText();

    void setInputText(@Nullable String text);
}
