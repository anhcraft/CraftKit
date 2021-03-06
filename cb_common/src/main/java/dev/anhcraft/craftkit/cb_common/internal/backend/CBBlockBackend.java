package dev.anhcraft.craftkit.cb_common.internal.backend;

import dev.anhcraft.craftkit.cb_common.BoundingBox;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface CBBlockBackend extends IBackend {
    void fakeBreak(int id, Block block, int stage, Player viewer);
    void fakeBreak(int id, Block block, int stage, Collection<Player> viewers);
    default boolean setBlockType(Block block, Object data, boolean physics, boolean light) {
        throw new UnsupportedOperationException("This method is currently unsupported");
    }
    default BoundingBox getBoundingBox(Block block) {
        return new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX() + 1, block.getY() + 1, block.getZ() + 1);
    }
    default void stopThreadPool() { }
}
