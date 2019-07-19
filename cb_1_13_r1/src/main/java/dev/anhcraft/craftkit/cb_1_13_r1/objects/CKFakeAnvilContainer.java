package dev.anhcraft.craftkit.cb_1_13_r1.objects;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.ContainerAnvil;
import net.minecraft.server.v1_13_R1.EntityHuman;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(EntityHuman e) {
        super(e.inventory, e.world, new BlockPosition(0,0,0), e);
        this.checkReachable = false;
    }
}