package dev.anhcraft.craftkit.cb_1_17_r1.objects;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.Slot;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(int id, EntityHuman e) {
        super(id, e.getInventory(), ContainerAccess.at(e.getWorld(), new BlockPosition(0, 0, 0)));
        this.checkReachable = false;
        this.i.set(2, new Slot(e.getInventory(), 2, 134, 4));
    }
}
