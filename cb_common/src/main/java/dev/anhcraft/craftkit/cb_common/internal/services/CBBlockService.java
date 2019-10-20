package dev.anhcraft.craftkit.cb_common.internal.services;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface CBBlockService extends CBService {
    void fakeBreak(int id, Block block, int stage, Player viewer);
    void fakeBreak(int id, Block block, int stage, Collection<Player> viewers);

    default BoundingBox getBoundingBox(Block block) {
        return new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX() + 1, block.getY() + 1, block.getZ() + 1);
    }
}