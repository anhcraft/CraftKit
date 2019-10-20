package examples;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.builders.ItemBuilder;
import dev.anhcraft.craftkit.cb_common.callbacks.gui.SlotCallback;
import dev.anhcraft.craftkit.cb_common.gui.BaseGUI;
import dev.anhcraft.craftkit.cb_common.gui.CustomGUI;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.craftkit.utils.InventoryUtil;
import dev.anhcraft.craftkit.utils.MaterialUtil;
import dev.anhcraft.jvmkit.utils.RandomUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RainbowGUI {
    // init the extension here!
    // CraftExtension.of(<YOUR PLUGIN'S MAIN CLASS>)
    private CraftExtension extension;

    public void open(Player player){
        CustomGUI cg = extension.createCustomGUI(null, 45, ChatUtil.formatColorCodes("&aRainbow &fInventory"));
        InventoryUtil.fillAll(cg, new ItemBuilder(Material.APPLE)
                .name("&a")
                .build());
        cg.addContentCallback(SlotCallback.PREVENT_MODIFY);
        cg.addContentCallback(new SlotCallback() {
            @Override
            public void click(InventoryClickEvent event, Player player, BaseGUI gui) {
                InventoryUtil.fillAll(cg, new ItemBuilder(RandomUtil.pickRandom(MaterialUtil.getStainedGlassPaneTypes())).name("&a").build());
                player.damage(0.0001);
            }
        });
        player.openInventory(cg);
    }
}
