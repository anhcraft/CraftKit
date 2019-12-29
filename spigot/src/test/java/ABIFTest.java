import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.exception.InvalidValueException;
import dev.anhcraft.craftkit.abif.MetaType;
import dev.anhcraft.craftkit.abif.PreparedItem;
import dev.anhcraft.craftkit.attribute.Attribute;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.craftkit.attribute.Modifier;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.junit.Test;

public class ABIFTest {
    @Test
    public void test1(){
        PreparedItem pi = new PreparedItem();
        pi.amount(3);
        pi.name("Test item");
        pi.material(Material.DIAMOND_AXE);
        pi.lore().add("This is the first line");
        pi.lore().add("This is the second line");
        pi.lore().add("This is the third line");
        pi.flags().add(ItemFlag.HIDE_UNBREAKABLE);
        pi.itemModifiers().add(new ItemModifier("name", 3, Modifier.Operation.ADD_NUMBER, Attribute.GENERIC_ARMOR, null));
        pi.itemModifiers().add(new ItemModifier("name", 3, Modifier.Operation.ADD_NUMBER, Attribute.GENERIC_ARMOR, EquipmentSlot.CHEST));
        pi.metaType(MetaType.SKULL);
        pi.skullOwner("a");
        YamlConfiguration configuration = new YamlConfiguration();
        ConfigHelper.writeConfig(configuration, PreparedItem.SCHEMA, pi, ConfigHelper.newOptions().ignoreZero().ignoreFalse().ignoreEmptySection().ignoreEmptyList().ignoreEmptyArray());
        System.out.println(configuration.saveToString());
        try {
            ConfigHelper.readConfig(configuration, PreparedItem.SCHEMA, pi);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
    }
}
