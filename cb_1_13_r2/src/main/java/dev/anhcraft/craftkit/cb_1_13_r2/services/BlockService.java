package dev.anhcraft.craftkit.cb_1_13_r2.services;

import dev.anhcraft.craftkit.cb_1_13_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.internal.CBBlockService;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.PacketPlayOutBlockBreakAnimation;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class BlockService extends CBModule implements CBBlockService {
    @Override
    public void fakeBreak(int id, Block block, int stage, List<Player> viewers) {
        stage = Math.min(Math.max(0, stage), 9);
        BlockPosition pos = new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY(),
                block.getLocation().getBlockZ());
        sendPacket(new PacketPlayOutBlockBreakAnimation(id, pos, stage), toEntityPlayers(viewers));
    }
}
