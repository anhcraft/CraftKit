package dev.anhcraft.craftkit.cb_1_12_r1;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CBModule {
    protected static final CraftServer craftServer = (CraftServer) Bukkit.getServer();
    protected static final MinecraftServer minecraftServer = craftServer.getServer();

    protected void sendPacket(Packet packet, EntityPlayer receiver) {
        receiver.playerConnection.sendPacket(packet);
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
