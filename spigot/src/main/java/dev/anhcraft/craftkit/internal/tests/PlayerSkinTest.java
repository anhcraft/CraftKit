package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.GuiCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.common.Skin;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerSkinTest implements ITest {
    @Override
    public @NotNull String name() {
        return "PlayerSkin Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        PlayerUtil.changeSkin(player, new Skin("ewogICJ0aW1lc3RhbXAiIDogMTU5MzM0NzQ1MDEwNSwKICAicHJvZmlsZUlkIiA6ICJhNmE3MzI2NjZhZTI0YjIwYWQyNmIzYWZkZWZjNmM1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNeXN0aWNHYW1lck1hbiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kMjFkZjI2NzE1NTk5YmQxNzFmODlhMjM3Yzk0MDk4MDg0YzIxZDA5YWYwNTc2OTE2MDQ5NmJhYWUwMGFmYjE3IgogICAgfQogIH0KfQ==", "qKuYdajYrBDP/g3htw053uU4af2nssjd2TGs8MbedJJlzxO4M4ZmbAEa/a7CrSTseYPyXQagZ8rab/C1yaFc0FIF5ruW2CfZmLZWcTaFNJVYhqhneB6KmCDBXcGcyA+ZBTiARhBUiTNDVczu/wKLMTi3g0mi+ij2jylto6YRbF1Dwgz/naLu5E7hXLE9Ka8WLcSot/tsNl0ghZ5B+Sl/7sR7pWKnJJmkGou88rMOHPNYeusSEce1BUtx5C/6SXnEfQJeHtddi/hVQJcOlLNjfxAoL5ktf94a/c/xD27ubk3Lv1c+xj3+KC6QUhmcYsO+A8XtS4joAJRwnWpmwpZ2GVeSYZnKJD3VyBtb84hiJa/Dv8CaszhMUWC2EocfS9DDUBDzNlS518BADVrPOCxDm3WfMDtGgawOevRUz6tK7TeMh8TIBrUpdBhjm6Pr8id22mwKcB3OYKc4D//E/BeEAojE8KBlmOG9bOf3OtD3tRkSa40Yq+/xHP7oaPOdvc7M6PY0osY+Az0diqr0UPGVahcbXEekAzSyLagxJYEy4KAVPzSzgFDWks5pLK2xUSRlyAJnI1YbzCKYZOaHq3nfQGZgyJQbjeAOLvspk4R5+cmWsR3C/EO77T5TdPNRn8dk/W1Bii+k6+hnNuSoqWM00QooUZ2wBhmteNDfLNRVsnU="));
        CraftExtension extension = CraftExtension.of(CraftKit.class);
        extension.getTaskHelper().newDelayedTask(() -> {
            CustomGUI customGUI = extension.createCustomGUI(null, 9, ChatColor.DARK_GREEN + "Did skin work?");
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
                    if (!chain.isFinished() && chain.getCurrentTest().equals(name())) {
                        extension.getTaskHelper().newDelayedTask(() -> {
                            player.openInventory(customGUI);
                        }, 60);
                    }
                }
            });
            player.openInventory(customGUI);
        }, 100);
    }
}
