package dev.anhcraft.craftkit.cb_1_13_r2.services;

import dev.anhcraft.craftkit.cb_1_13_r2.CBModule;
import dev.anhcraft.craftkit.cb_1_13_r2.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_1_13_r2.objects.GUIAnvil;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBAnvilBackend;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import static dev.anhcraft.jvmkit.utils.ReflectionUtil.getDeclaredField;
import static dev.anhcraft.jvmkit.utils.ReflectionUtil.setDeclaredField;

public class AnvilBackend extends CBModule implements CBAnvilBackend {
    @Override
    public InventoryView create(Player player, String title) {
        EntityPlayer ep = toEntityPlayer(player);
        int id = ep.nextContainerCounter();
        ChatMessage t = new ChatMessage(title);

        sendPacket(new PacketPlayOutOpenWindow(id, "minecraft:anvil", t), ep);

        CKFakeAnvilContainer cont = new CKFakeAnvilContainer(ep);
        cont.windowId = id;
        ep.activeContainer = cont;
        ep.activeContainer.addSlotListener(ep);

        IInventory resultInv = (IInventory) getDeclaredField(ContainerAnvil.class, cont, "resultInventory");
        IInventory repairInv = (IInventory) getDeclaredField(ContainerAnvil.class, cont, "repairInventory");
        BlockPosition pos = (BlockPosition) getDeclaredField(ContainerAnvil.class, cont, "position");
        GUIAnvil anvil = new GUIAnvil(new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ()), repairInv, resultInv, cont);
        CraftInventoryView view = new CraftInventoryView(player, anvil, cont);
        setDeclaredField(ContainerAnvil.class, cont, "bukkitEntity", view);
        return view;
    }
}
