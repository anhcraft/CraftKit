package dev.anhcraft.craftkit.cb_1_12_r1.services;

import dev.anhcraft.craftkit.cb_1_12_r1.CBModule;
import dev.anhcraft.craftkit.cb_1_12_r1.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_1_12_r1.objects.GUIAnvil;
import dev.anhcraft.craftkit.cb_common.internal.services.CBAnvilService;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import static dev.anhcraft.jvmkit.utils.ReflectionUtil.getDeclaredField;
import static dev.anhcraft.jvmkit.utils.ReflectionUtil.setDeclaredField;

public class AnvilService extends CBModule implements CBAnvilService {
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

        IInventory resultInv = (IInventory) getDeclaredField(ContainerAnvil.class, cont, "g");
        IInventory repairInv = (IInventory) getDeclaredField(ContainerAnvil.class, cont, "h");
        BlockPosition pos = (BlockPosition) getDeclaredField(ContainerAnvil.class, cont, "j");
        GUIAnvil anvil = new GUIAnvil(new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ()), repairInv, resultInv, cont);
        CraftInventoryView view = new CraftInventoryView(player, anvil, cont);
        setDeclaredField(ContainerAnvil.class, cont, "bukkitEntity", view);
        return view;
    }
}
