package dev.anhcraft.craftkit.cb_1_16_r3.services;

import dev.anhcraft.craftkit.cb_1_16_r3.CBModule;
import dev.anhcraft.craftkit.cb_1_16_r3.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_1_16_r3.objects.GUIAnvil;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBAnvilBackend;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryView;
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

        IInventory resultInv = (IInventory) getDeclaredField(ContainerAnvilAbstract.class, cont, "resultInventory");
        IInventory repairInv = (IInventory) getDeclaredField(ContainerAnvilAbstract.class, cont, "repairInventory");
        ContainerAccess contAccess = (ContainerAccess) getDeclaredField(ContainerAnvilAbstract.class, cont, "containerAccess");
        GUIAnvil anvil = new GUIAnvil(Objects.requireNonNull(contAccess).getLocation(), repairInv, resultInv, cont);
        CraftInventoryView view = new CraftInventoryView(player, anvil, cont);
        setDeclaredField(ContainerAnvil.class, cont, "bukkitEntity", view);
        return view;
    }
}
