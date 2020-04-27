package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.utils.InventoryUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class InventoryTest2 implements ITest {
    @Override
    public @NotNull String name() {
        return "Inventory Test 2";
    }

    private void a(CustomGUI gui, TestChain chain){
        InventoryUtil.fillAll(gui, new ItemStack(Material.DIAMOND, 1));
        gui.clearContentCallbacks();
        gui.setItem(ThreadLocalRandom.current().nextInt(gui.getSize()), new ItemBuilder(Material.NETHER_STAR)
                .name("Left-click me!")
                .lore("Right-click to go to next test").build(), new SlotCallback() {
            @Override
            public void click(InventoryClickEvent event, Player player, BaseGUI gui) {
                event.setCancelled(true);
                if(event.isRightClick()) {
                    player.closeInventory();
                    chain.report(true, null);
                } else {
                    a((CustomGUI) gui, chain);
                }
            }
        });
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        CraftExtension extension = CraftExtension.of(CraftKit.class);
        CustomGUI customGUI = extension.createCustomGUI(null, 36, ChatColor.RED + "Inventory 2");
        a(customGUI, chain);
        player.openInventory(customGUI);
    }
}
