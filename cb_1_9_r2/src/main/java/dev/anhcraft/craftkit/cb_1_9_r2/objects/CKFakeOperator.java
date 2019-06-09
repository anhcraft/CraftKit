package dev.anhcraft.craftkit.cb_1_9_r2.objects;

import dev.anhcraft.craftkit.cb_common.kits.entity.FakeOperator;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.permissions.Permission;

public class CKFakeOperator extends CraftPlayer implements FakeOperator {
    public CKFakeOperator(CraftServer server, EntityPlayer entity) {
        super(server, entity);
    }

    @Override
    public boolean hasPermission(String name) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return true;
    }
}
