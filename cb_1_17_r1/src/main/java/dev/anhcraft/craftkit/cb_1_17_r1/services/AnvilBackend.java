package dev.anhcraft.craftkit.cb_1_17_r1.services;

import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_1_17_r1.objects.CKFakeAnvilContainer;
import dev.anhcraft.craftkit.cb_1_17_r1.objects.GUIAnvil;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBAnvilBackend;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.inventory.*;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftInventoryView;
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

        sendPacket(new PacketPlayOutOpenWindow(id, Containers.h, t), ep);

        CKFakeAnvilContainer cont = new CKFakeAnvilContainer(id, ep);
        cont.setTitle(t);
        ep.bV = cont;
        ep.bV.addSlotListener((ICrafting) getDeclaredField(EntityPlayer.class, ep, "cX"));

        IInventory resultInv = (IInventory) getDeclaredField(ContainerAnvilAbstract.class, cont, "o");
        IInventory repairInv = (IInventory) getDeclaredField(ContainerAnvilAbstract.class, cont, "p");
        ContainerAccess contAccess = (ContainerAccess) getDeclaredField(ContainerAnvilAbstract.class, cont, "containerAccess");
        GUIAnvil anvil = new GUIAnvil(Objects.requireNonNull(contAccess).getLocation(), repairInv, resultInv, cont);
        CraftInventoryView view = new CraftInventoryView(player, anvil, cont);
        setDeclaredField(ContainerAnvil.class, cont, "bukkitEntity", view);
        return view;
    }
}
