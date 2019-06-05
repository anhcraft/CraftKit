package dev.anhcraft.craftkit.cb_1_13_r1.services;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_1_13_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBEntityNPCService;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.PlayerInteractManager;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;

public class EntityNPCService extends CBModule implements CBEntityNPCService {
    @Override
    public Object create(GameProfile profile, World world) {
        var worldServer = ((CraftWorld) world).getHandle();
        var pim = new PlayerInteractManager(worldServer);
        return new EntityPlayer(minecraftServer, worldServer, profile, pim);
    }
}
