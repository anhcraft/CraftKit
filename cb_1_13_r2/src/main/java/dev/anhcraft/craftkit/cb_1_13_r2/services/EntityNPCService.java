package dev.anhcraft.craftkit.cb_1_13_r2.services;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_1_13_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBEntityNPCService;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;
import net.minecraft.server.v1_13_R2.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

public class EntityNPCService extends CBModule implements CBEntityNPCService {
    @Override
    public Object create(GameProfile profile, World world) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        PlayerInteractManager pim = new PlayerInteractManager(worldServer);
        return new EntityPlayer(minecraftServer, worldServer, profile, pim);
    }
}
