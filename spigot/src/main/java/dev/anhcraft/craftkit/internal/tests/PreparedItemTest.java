package dev.anhcraft.craftkit.internal.tests;

import dev.anhcraft.config.bukkit.struct.YamlConfigSection;
import dev.anhcraft.craftkit.abif.PreparedItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PreparedItemTest implements ITest {
    @Override
    public @NotNull String name() {
        return "PreparedItem Test";
    }

    @Override
    public void run(@NotNull Player player, @NotNull TestChain chain) {
        PreparedItem pi = new PreparedItem();
        pi.material(Material.DIAMOND_SWORD);
        pi.enchants().put(Enchantment.DAMAGE_ALL, 3);
        pi.lore().add("A strong sword");
        try {
            pi = PreparedItem.of(pi.saveTo(new YamlConfigSection()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.getInventory().addItem(pi.build());
        chain.report(true, null);
    }
}
