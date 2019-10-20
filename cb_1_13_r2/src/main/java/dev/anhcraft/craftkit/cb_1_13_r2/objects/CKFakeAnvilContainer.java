package dev.anhcraft.craftkit.cb_1_13_r2.objects;

import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.ContainerAnvil;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.Slot;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(EntityHuman e) {
        super(e.inventory, e.world, new BlockPosition(0,0,0), e);
        this.checkReachable = false;
        slots.set(2, new Slot(e.inventory, 2, 134, 47));
    }
}
