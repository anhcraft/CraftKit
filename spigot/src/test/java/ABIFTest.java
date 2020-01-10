import com.google.common.collect.ImmutableList;
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
import org.bukkit.inventory.meta.BookMeta;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ABIFTest {
    @Test
    public void test1(){
        PreparedItem pi = new PreparedItem();
        pi.name("Steve's diary");
        pi.material(Material.WRITTEN_BOOK);
        pi.lore().add("This is the diary of Steve");
        pi.flags().add(ItemFlag.HIDE_UNBREAKABLE);
        pi.itemModifiers().add(new ItemModifier("1", 1, Modifier.Operation.ADD_NUMBER, Attribute.GENERIC_ATTACK_DAMAGE, EquipmentSlot.OFF_HAND));
        pi.metaType(MetaType.BOOK);
        pi.bookTitle("Steve's diary");
        pi.bookGeneration(BookMeta.Generation.ORIGINAL);
        pi.bookAuthor("Steve");
        pi.bookPages(ImmutableList.of("My first day,...", "My second day,...", "The next day,..."));
        YamlConfiguration configuration = new YamlConfiguration();
        ConfigHelper.writeConfig(configuration, PreparedItem.SCHEMA, pi, ConfigHelper.newOptions().ignoreZero().ignoreFalse().ignoreEmptySection().ignoreEmptyList().ignoreEmptyArray());
        System.out.println(Arrays.stream(configuration.saveToString().split("\n")).map(s -> "\"" + s + "\",").collect(Collectors.joining("\n")));
        try {
            ConfigHelper.readConfig(configuration, PreparedItem.SCHEMA, pi);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
    }
}
