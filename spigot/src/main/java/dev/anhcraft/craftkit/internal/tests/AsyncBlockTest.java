package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.utils.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AsyncBlockTest implements ITest {
    @Override
    public @NotNull String name() {
        return "Async Block Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        if(NMSVersion.current().compare(NMSVersion.v1_12_R1) <= 0) {
            chain.report(true, null);
            return;
        }
        CraftExtension.of(CraftKit.class).getTaskHelper().newAsyncTask(() -> {
            for (Block b : BlockUtil.getNearbyBlocks(player.getLocation(), 3, 3, 3)) {
                BlockUtil.setBlockFast(b, Material.GLOWSTONE, false, false);
            }
            CraftExtension.of(CraftKit.class).getTaskHelper().newTask(() -> chain.report(true, null));
        });
    }
}
