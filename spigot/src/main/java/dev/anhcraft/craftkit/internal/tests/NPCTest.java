package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.GameProfileBuilder;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.common.Skin;
import dev.anhcraft.craftkit.entity.NPC;
import dev.anhcraft.craftkit.internal.CraftKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NPCTest implements ITest {
    @Override
    public @NotNull String name() {
        return "NPC Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        NPC npc = NPC.spawn(new GameProfileBuilder(UUID.randomUUID(), "abcxyz").setSkin(new Skin("ewogICJ0aW1lc3RhbXAiIDogMTU5MzIxOTUwNzI4NiwKICAicHJvZmlsZUlkIiA6ICI5ZDEzZjcyMTcxM2E0N2U0OTAwZTMyZGVkNjBjNDY3MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUYWxvZGFvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ1YmM1YWViN2M3MDQ2OWFjNDJmODFhZWI5MTAwNDE2NzkyZWM5YzFiNmJkY2Q2OGI4YzA2MTliN2NiZDdjZGIiCiAgICB9CiAgfQp9", "bnbEECBzyDhs+zjumyUjKCNgOr23910lh+OZG1YLFSMLfyqrEvY+8Xx/q6PwNLLHt0+uC7dcuKJ2/9wqKtPsYNCkoiKzy2YY23RzJ0GVmDoMSqsAPvjB/JEJDbMULh+9kLJC1iCjsPQYk5iv5isDoANV4BzXQIW7gVrgtsHKkK0O6OY7mBiJOaZrYpP9g9XnLaJYJcS0XrWx73yzgn9Er3zK9nBWbpJulTYG4DiWQexvaTtRup2itLab4i1OK6IQV1Zi+gjKKYvZZDE+VZpD5uwf/kXkg60kozbclUorjmnwKFwnkl0FV+8e8dhigdNt5UoNalcVxMUFaEMyX5n/7KKv3+lk+rrQvlQCtsyhniInP3KU277uNnY9qjXfuZ2Nd+uhUaVoRl0sw1S136D4x04M5NPCr4yTNE2SwNj6KBillRODwu+SKr6AHOxnLJFLhG+Wz4C8AZ8MkN0B8oKxyvUsFSKGJ7AlVhjdSZfMXsImUobcfPGlrJsay6xbRvsaj5Gl8nK6/ciWocDQdYw8Gh3PGf589m26Kbeg9pqlrudOKxknnVOu3DS2VvdPMJ8lQW7iSjoVqOanmureg2Hz4KQ/GVFwYcW2q2D94mxZRFfmOYUJ95hqtOHeObDI/lQ8PxFvePH9TpCdTuGtTT67NXM9uuim/+i7WP3zmZbcyaQ=")).build(), player.getLocation());
        npc.addViewer(player);
        CraftExtension extension = CraftExtension.of(CraftKit.class);
        CustomGUI customGUI = extension.createCustomGUI(null, 9, ChatColor.RED +  "Does NPC look ok?");
        customGUI.addItem(new ItemBuilder(Material.DIAMOND).name("Confirm ok").build(), new SlotCallback() {
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
    }
}
