package dev.anhcraft.craftkit.cb_1_15_r1.services;

import dev.anhcraft.craftkit.cb_1_15_r1.CBModule;
import dev.anhcraft.craftkit.cb_1_15_r1.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_1_15_r1.objects.GUIAnvil;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBAnvilBackend;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.util.Objects;

import static dev.anhcraft.jvmkit.utils.ReflectionUtil.getDeclaredField;
import static dev.anhcraft.jvmkit.utils.ReflectionUtil.setDeclaredField;

public class AnvilBackend extends CBModule implements CBAnvilBackend {
    @Override
    public InventoryView create(Player player, String title) {
        EntityPlayer ep = toEntityPlayer(player);
        int id = ep.nextContainerCounter();
        ChatMessage t = new ChatMessage(title);

        sendPacket(new PacketPlayOutOpenWindow(id, Containers.ANVIL, t), ep);

        CKFakeAnvilContainer cont = new CKFakeAnvilContainer(id, ep);
        cont.setTitle(t);
        ep.activeContainer = cont;
        ep.activeContainer.addSlotListener(ep);

        IInventory resultInv = (IInventory) getDeclaredField(ContainerAnvil.class, cont, "resultInventory");
        IInventory repairInv = (IInventory) getDeclaredField(ContainerAnvil.class, cont, "repairInventory");
        ContainerAccess contAccess = (ContainerAccess) getDeclaredField(ContainerAnvil.class, cont, "containerAccess");
        GUIAnvil anvil = new GUIAnvil(Objects.requireNonNull(contAccess).getLocation(), repairInv, resultInv, cont);
        CraftInventoryView view = new CraftInventoryView(player, anvil, cont);
        setDeclaredField(ContainerAnvil.class, cont, "bukkitEntity", view);
        return view;
    }
}
