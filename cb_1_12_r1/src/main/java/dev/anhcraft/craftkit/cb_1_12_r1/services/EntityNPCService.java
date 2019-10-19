package dev.anhcraft.craftkit.cb_1_12_r1.services;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_1_12_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.services.CBEntityNPCService;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

public class EntityNPCService extends CBModule implements CBEntityNPCService {
    @Override
    public Object create(GameProfile profile, World world) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        PlayerInteractManager pim = new PlayerInteractManager(worldServer);
        return new EntityPlayer(minecraftServer, worldServer, profile, pim);
    }
}
