package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.internal.CraftKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class InventoryTest1 implements ITest {
    @Override
    public @NotNull String name() {
        return "Inventory Test 1";
    }

    private void a(CustomGUI gui, int slot){
        gui.setItem(slot, new ItemBuilder(Material.NETHER_STAR).name("Click me!").build(), new SlotCallback() {
            @Override
            public void click(InventoryClickEvent event, Player player, BaseGUI gui) {
                event.setCurrentItem(null);
                gui.clearSlotCallbacks(event.getSlot());
                a((CustomGUI) gui, event.getSlot() + 1);
            }
        });
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        CraftExtension extension = CraftExtension.of(CraftKit.class);
        CustomGUI customGUI = extension.createCustomGUI(null, 45, ChatColor.GOLD +  "Inventory 1");
        customGUI.addItem(new ItemBuilder(Material.DIAMOND).name("Click to go to next test").build(), new SlotCallback() {
            @Override
            public void click(InventoryClickEvent event, Player player, BaseGUI gui) {
                player.closeInventory();
                chain.report(true, null);
            }
        });
        a(customGUI, 1);
        player.openInventory(customGUI);
    }
}
