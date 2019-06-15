package dev.anhcraft.craftkit.cb_1_14_r1.services;

import dev.anhcraft.craftkit.cb_1_14_r1.CBModule;
import dev.anhcraft.craftkit.cb_1_14_r1.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_common.internal.CBAnvilService;
import net.minecraft.server.v1_14_R1.ChatMessage;
import net.minecraft.server.v1_14_R1.Container;
import net.minecraft.server.v1_14_R1.Containers;
import net.minecraft.server.v1_14_R1.PacketPlayOutOpenWindow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public class AnvilService extends CBModule implements CBAnvilService {
    @Override
    public void open(Player player, Consumer<Inventory> inv) {
        var ep = toEntityPlayer(player);
        var id = ep.nextContainerCounter();
        var container = new CKFakeAnvilContainer(id, ep);
        var civ = container.getBukkitView();
        inv.accept(civ.getTopInventory());
        ep.playerConnection.sendPacket(new PacketPlayOutOpenWindow(id,
                Containers.ANVIL, new ChatMessage("tile.anvil.name")));
        ep.activeContainer = container;
        try {
            Field field = Container.class.getDeclaredField("windowId");
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        ep.activeContainer.addSlotListener(ep);
    }
}
