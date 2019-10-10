import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.exception.InvalidValueException;
import dev.anhcraft.craftkit.abif.PreparedItem;
import dev.anhcraft.craftkit.attribute.Attribute;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.craftkit.attribute.Modifier;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
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
        pi.flags().add(ItemFlag.HIDE_UNBREAKABLE);
        pi.getItemModifiers().add(new ItemModifier("name", 3, Modifier.Operation.ADD_NUMBER, Attribute.GENERIC_ARMOR, null));
        pi.getItemModifiers().add(new ItemModifier("name", 3, Modifier.Operation.ADD_NUMBER, Attribute.GENERIC_ARMOR, EquipmentSlot.CHEST));
        YamlConfiguration configuration = new YamlConfiguration();
        ConfigHelper.writeConfig(configuration, PreparedItem.SCHEMA, pi);
        System.out.println(configuration.saveToString());
        try {
            ConfigHelper.readConfig(configuration, PreparedItem.SCHEMA, pi);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
    }
}
