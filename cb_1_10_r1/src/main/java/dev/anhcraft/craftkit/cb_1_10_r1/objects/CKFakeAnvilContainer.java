package dev.anhcraft.craftkit.cb_1_10_r1.objects;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.ContainerAnvil;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.Slot;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(EntityHuman e) {
        super(e.inventory, e.world, new BlockPosition(0,0,0), e);
        c.set(2, new Slot(e.inventory, 2, 134, 47));
    }

    @Override
    public boolean a(EntityHuman e) {
        return true;
    }
}
