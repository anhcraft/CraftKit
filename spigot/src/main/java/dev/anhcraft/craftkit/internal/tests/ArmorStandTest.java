package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.entity.ArmorStand;
import dev.anhcraft.craftkit.internal.CraftKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArmorStandTest implements ITest {
    @Override
    public @NotNull String name() {
        return "ArmorStand Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        ArmorStand as = ArmorStand.spawn(player.getLocation());
        as.setArms(true);
        as.setBasePlate(true);
        as.setEquipment(EquipmentSlot.HEAD, new ItemStack(Material.DIAMOND_HELMET, 1));
        as.setName("A-R-M-O-R S-T-A-N-D");
        as.setNameVisible(true);
        as.addViewer(player);
        CraftExtension extension = CraftExtension.of(CraftKit.class);
        extension.getTaskHelper().newDelayedAsyncTask(() -> {
            as.setEquipment(EquipmentSlot.CHEST, new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
            as.setEquipment(EquipmentSlot.HAND, new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DURABILITY, 1).build());
            as.sendUpdate();
            extension.getTaskHelper().newDelayedTask(() -> {
                CustomGUI customGUI = extension.createCustomGUI(null, 9, ChatColor.GOLD +  "Does ArmorStand look ok?");
                customGUI.addItem(new ItemBuilder(Material.DIAMOND)
                        .name("Confirm ok")
                        .lore("- Having arms and base plate")
                        .lore("- Wearing diamond helmet and diamond chest plate")
                        .lore("- Holding enchanted diamond sword")
                        .lore("- Has name tag enabled")
                        .build(), new SlotCallback() {
                    @Override
                    public void click(InventoryClickEvent event, Player player, BaseGUI gui) {
                        chain.report(true, null);
                        player.closeInventory();
                    }
                });
                customGUI.addItem(new ItemBuilder(Material.REDSTONE).name("Something wrong...").build(), new SlotCallback() {
                    @Override
                    public void click(InventoryClickEvent event, Player player, BaseGUI gui) {
                        chain.report(false, null);
                        player.closeInventory();
                    }
                });
                customGUI.addInterfaceCallback(new GuiCallback() {
                    @Override
                    public void close(InventoryCloseEvent event, Player player, BaseGUI gui) {
                        if(!chain.isFinished() && chain.getCurrentTest().equals(name())) {
                            extension.getTaskHelper().newDelayedTask(() -> {
                                player.openInventory(customGUI);
                            }, 60);
                        }
                    }
                });
                player.openInventory(customGUI);
            }, 40);
        }, 40);
    }
}
