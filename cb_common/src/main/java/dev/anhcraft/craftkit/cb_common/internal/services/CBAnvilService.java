package dev.anhcraft.craftkit.cb_common.internal.services;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public interface CBAnvilService extends CBService {
    InventoryView create(Player player, String title);
}
