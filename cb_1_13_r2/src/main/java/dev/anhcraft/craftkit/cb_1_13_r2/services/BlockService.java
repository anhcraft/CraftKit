package dev.anhcraft.craftkit.cb_1_13_r2.services;

import dev.anhcraft.craftkit.cb_1_13_r2.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.CBBlockService;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlock;
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

    @Override
    public BoundingBox getBoundingBox(Block block) {
        CraftBlock craftBlock = (CraftBlock) block;
        IBlockAccess blockAccess = craftBlock.getCraftWorld().getHandle();
        IBlockData blockData = craftBlock.getNMS();
        VoxelShape vs = blockData.getBlock().a(blockData, blockAccess, craftBlock.getPosition());
        if(vs.isEmpty()) return new BoundingBox();
        else {
            AxisAlignedBB aabb = vs.getBoundingBox();
            return new BoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
        }
    }
}
