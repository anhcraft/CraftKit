package dev.anhcraft.craftkit.cb_1_12_r1.services;

import dev.anhcraft.craftkit.cb_1_12_r1.CBModule;
import dev.anhcraft.craftkit.cb_1_12_r1.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_common.internal.CBAnvilService;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.ContainerAnvil;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public class AnvilService extends CBModule implements CBAnvilService {
    @Override
    public void open(Player player, Consumer<Inventory> inv) {
        var ep = toEntityPlayer(player);
        ContainerAnvil container = new CKFakeAnvilContainer(ep);
        CraftInventoryView civ = container.getBukkitView();
        inv.accept(civ.getTopInventory());
        int id = ep.nextContainerCounter();
        ep.playerConnection.sendPacket(new PacketPlayOutOpenWindow(id,
                "minecraft:anvil", new ChatMessage("tile.anvil.name"), 0));
        ep.activeContainer = container;
        ep.activeContainer.windowId = id;
        ep.activeContainer.addSlotListener(ep);
    }
}
