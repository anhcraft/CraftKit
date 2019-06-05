package dev.anhcraft.craftkit.cb_common.internal;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public interface CBBlockService extends CBService {
    void fakeBreak(int id, Block block, int stage, List<Player> viewers);
}
