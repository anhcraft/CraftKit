package dev.anhcraft.craftkit.cb_common.internal;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public interface CBAnvilService extends CBService {
    void open(Player player, Consumer<Inventory> inv);
}
