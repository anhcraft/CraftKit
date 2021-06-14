package dev.anhcraft.craftkit.cb_1_17_r1.services;

import com.mojang.authlib.GameProfile;
import dev.anhcraft.craftkit.cb_1_17_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBEntityNPCBackend;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

public class EntityNPCBackend extends CBModule implements CBEntityNPCBackend {
    @Override
    public Object create(GameProfile profile, World world) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        return new EntityPlayer(minecraftServer, worldServer, profile);
    }
}
