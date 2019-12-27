package dev.anhcraft.craftkit.cb_1_15_r1.services;

import dev.anhcraft.craftkit.cb_1_15_r1.CBModule;
import dev.anhcraft.craftkit.cb_common.BoundingBox;
import dev.anhcraft.craftkit.cb_common.internal.services.CBBlockService;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftBlock;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BlockService extends CBModule implements CBBlockService {
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

    @Override
    public BoundingBox getBoundingBox(Block block) {
        CraftBlock craftBlock = (CraftBlock) block;
        IBlockAccess blockAccess = craftBlock.getCraftWorld().getHandle();
        IBlockData blockData = craftBlock.getNMS();
        VoxelShape vs = blockData.getShape(blockAccess, craftBlock.getPosition());
        if(vs.isEmpty()) return new BoundingBox();
        else {
            AxisAlignedBB aabb = vs.getBoundingBox();
            return new BoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
        }
    }
}