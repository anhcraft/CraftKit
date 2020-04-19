package dev.anhcraft.craftkit.cb_1_9_r2.services;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_1_9_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBEntityNPCBackend;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;
import net.minecraft.server.v1_9_R2.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

public class EntityNPCBackend extends CBModule implements CBEntityNPCBackend {
    @Override
    public Object create(GameProfile profile, World world) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        PlayerInteractManager pim = new PlayerInteractManager(worldServer);
        return new EntityPlayer(minecraftServer, worldServer, profile, pim);
    }
}
