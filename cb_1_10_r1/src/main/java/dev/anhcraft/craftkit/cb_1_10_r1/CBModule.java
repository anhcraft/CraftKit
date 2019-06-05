package dev.anhcraft.craftkit.cb_1_10_r1;

import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class CBModule {
    protected static final CraftServer craftServer = (CraftServer) Bukkit.getServer();
    protected static final MinecraftServer minecraftServer = craftServer.getServer();

    protected void sendPacket(Packet packet, EntityPlayer receiver) {
        receiver.playerConnection.sendPacket(packet);
    }

    protected void sendPacket(Packet packet, List<EntityPlayer> receivers) {
        receivers.forEach((a) -> sendPacket(packet, a));
    }

    protected EntityPlayer toEntityPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    protected List<EntityPlayer> toEntityPlayers(List<Player> x) {
        return x.stream().map(player -> ((CraftPlayer) player).getHandle()).collect(Collectors.toList());
    }

    protected List<EntityPlayer> castEntityPlayers(List<Object> x) {
        return x.stream().map(player -> (EntityPlayer) player).collect(Collectors.toList());
    }

    protected List<Entity> castEntities(List<Object> x) {
        return x.stream().map(player -> (Entity) player).collect(Collectors.toList());
    }
}
