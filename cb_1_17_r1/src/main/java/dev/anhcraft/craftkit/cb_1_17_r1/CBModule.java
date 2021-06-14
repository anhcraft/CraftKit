package dev.anhcraft.craftkit.cb_1_17_r1;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CBModule {
    protected static final CraftServer craftServer = (CraftServer) Bukkit.getServer();
    protected static final MinecraftServer minecraftServer = craftServer.getServer();

    protected void sendPacket(Packet packet, EntityPlayer receiver) {
        receiver.b.sendPacket(packet);
    }

    protected void sendPacket(Packet packet, Collection<EntityPlayer> receivers) {
        receivers.forEach((a) -> sendPacket(packet, a));
    }

    protected EntityPlayer toEntityPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    protected List<EntityPlayer> toEntityPlayers(Collection<Player> x) {
        return x.stream().map(player -> ((CraftPlayer) player).getHandle()).collect(Collectors.toList());
    }

    protected List<EntityPlayer> castEntityPlayers(Collection<Object> x) {
        return x.stream().map(player -> (EntityPlayer) player).collect(Collectors.toList());
    }

    protected List<Entity> castEntities(Collection<Object> x) {
        return x.stream().map(ent -> (Entity) ent).collect(Collectors.toList());
    }
}
