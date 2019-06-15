package dev.anhcraft.craftkit.cb_1_14_r1.objects;

import net.minecraft.server.v1_14_R1.*;

public class CKFakeAnvilContainer extends ContainerAnvil {
    public CKFakeAnvilContainer(int id, EntityHuman e) {
        super(id, e.inventory, ContainerAccess.at(e.world, new BlockPosition(0, 0, 0)));
        setTitle(new ChatMessage("tile.anvil.name"));
        this.checkReachable = false;
    }
}
