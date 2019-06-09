package dev.anhcraft.craftkit.cb_1_13_r1.objects;

import dev.anhcraft.craftkit.cb_common.kits.entity.FakeOperator;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_13_R1.CraftServer;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.permissions.Permission;

public class CKFakeOperator extends CraftPlayer implements FakeOperator {
    public CKFakeOperator(CraftServer server, EntityPlayer entity) {
        super(server, entity);
    }

    @Override
    public boolean isPermissionSet(String name) {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return true;
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {}

    @Override
    public boolean hasPermission(String name) {
        return true;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return true;
    }
}
