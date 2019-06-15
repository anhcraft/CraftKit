package dev.anhcraft.craftkit.cb_1_9_r2.objects;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.ContainerAnvil;
import net.minecraft.server.v1_9_R2.EntityHuman;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(EntityHuman e) {
        super(e.inventory, e.world, new BlockPosition(0,0,0), e);
    }

    @Override
    public boolean a(EntityHuman e) {
        return true;
    }
}
