package dev.anhcraft.craftkit.cb_1_10_r1.services;

import dev.anhcraft.craftkit.cb_1_10_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBBlockBackend;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.PacketPlayOutBlockBreakAnimation;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BlockBackend extends CBModule implements CBBlockBackend {
    @Override
    public void fakeBreak(int id, Block block, int stage, Player viewer) {
        BlockPosition pos = new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY(),
                block.getLocation().getBlockZ());
        sendPacket(new PacketPlayOutBlockBreakAnimation(id, pos, stage), toEntityPlayer(viewer));
    }

    @Override
    public void fakeBreak(int id, Block block, int stage, Collection<Player> viewers) {
        BlockPosition pos = new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY(),
                block.getLocation().getBlockZ());
        sendPacket(new PacketPlayOutBlockBreakAnimation(id, pos, stage), toEntityPlayers(viewers));
    }
}
