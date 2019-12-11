package dev.anhcraft.craftkit.cb_1_15_r1.objects;

import net.minecraft.server.v1_15_R1.*;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(int id, EntityHuman e) {
        super(id, e.inventory, ContainerAccess.at(e.world, new BlockPosition(0, 0, 0)));
        this.checkReachable = false;
        slots.set(2, new Slot(e.inventory, 2, 134, 4));
    }
}
