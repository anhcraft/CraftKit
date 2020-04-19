package dev.anhcraft.craftkit.cb_common.internal.backend;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public interface CBAnvilBackend extends IBackend {
    InventoryView create(Player player, String title);
}
