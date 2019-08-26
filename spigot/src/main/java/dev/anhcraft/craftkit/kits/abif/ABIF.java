package dev.anhcraft.craftkit.kits.abif;

import dev.anhcraft.craftkit.helpers.ItemNBTHelper;
import dev.anhcraft.craftkit.kits.attribute.ItemModifier;
import dev.anhcraft.craftkit.kits.attribute.Modifier;
import dev.anhcraft.craftkit.lang.enumeration.Attribute;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the clone of ABIF (https://github.com/anhcraft/ABIF/) which brings multi-version support.
 */
public class ABIF {
    private static class Key{
        static String MATERIAL = "material";
        static String AMOUNT = "amount";
        static String DAMAGE = "damage";
        static String NAME = "name";
        static String LORE = "lore";
        static String ENCHANT = "enchant";
        static String FLAG = "flag";
        static String UNBREAKABLE = "unbreakable";
        static String ATTRIBUTE = "attr";
    }

    private static final Map<String, Enchantment> ENCHANT_MAP = new HashMap<>();
    private static final Map<Enchantment, String> REVERSED_ENCHANT_MAP = new HashMap<>();
    private static final List<String> AVAILABLE_VANILLA_ENCHANTS = Arrays
            .stream(Enchantment.values())
            .map(Enchantment::getName)
            .collect(Collectors.toList());

    static {
        registerEnchant("protection", "PROTECTION_ENVIRONMENTAL");
        registerEnchant("fire protection", "PROTECTION_FIRE");
        registerEnchant("feather falling", "PROTECTION_FALL");
        registerEnchant("blast protection", "PROTECTION_EXPLOSIONS");
        registerEnchant("projectile protection", "PROTECTION_PROJECTILE");
        registerEnchant("respiration", "OXYGEN");
        registerEnchant("aqua affinity", "WATER_WORKER");
        registerEnchant("thorns", "THORNS");
        registerEnchant("depth strider", "DEPTH_STRIDER");
        registerEnchant("sharpness", "DAMAGE_ALL");
        registerEnchant("smite", "DAMAGE_UNDEAD");
        registerEnchant("bane of arthropods", "DAMAGE_ARTHROPODS");
        registerEnchant("knockback", "KNOCKBACK");
        registerEnchant("fire aspect", "FIRE_ASPECT");
        registerEnchant("looting", "LOOT_BONUS_MOBS");
        registerEnchant("efficiency", "DIG_SPEED");
        registerEnchant("silk touch", "SILK_TOUCH");
        registerEnchant("unbreaking", "DURABILITY");
        registerEnchant("fortune", "LOOT_BONUS_BLOCKS");
        registerEnchant("power", "ARROW_DAMAGE");
        registerEnchant("punch", "ARROW_KNOCKBACK");
        registerEnchant("flame", "ARROW_FIRE");
        registerEnchant("infinity", "ARROW_INFINITE");
        registerEnchant("luck of the sea", "LUCK");
        registerEnchant("lure", "LURE");
        registerEnchant("mending", "MENDING");
        registerEnchant("frost walker", "FROST_WALKER");
        registerEnchant("curse of binding", "BINDING_CURSE");
        registerEnchant("curse of vanishing", "VANISHING_CURSE");
        registerEnchant("sweeping edge", "SWEEPING_EDGE");
        registerEnchant("loyalty", "LOYALTY");
        registerEnchant("impaling", "IMPALING");
        registerEnchant("riptide", "RIPTIDE");
        registerEnchant("channeling", "CHANNELING");
        registerEnchant("multishot", "MULTISHOT");
        registerEnchant("quick charge", "QUICK_CHARGE");
        registerEnchant("piercing", "PIERCING");
    }

    private static void registerEnchant(String encName, String enumName){
        if(AVAILABLE_VANILLA_ENCHANTS.contains(enumName)) {
            Enchantment enc = Enchantment.getByName(enumName);
            ENCHANT_MAP.put(enumName.toLowerCase(), enc);
            registerEnchant(encName, enc);
        }
    }

    /**
     * Registers the given enchantment, so it can be handled by ABIF.
     * @param encName the name of the enchantment (can contain spaces between words)
     * @param enchantment an instance of {@link Enchantment}
     */
    public static void registerEnchant(String encName, Enchantment enchantment){
        ENCHANT_MAP.put(encName.toLowerCase(), enchantment);
        ENCHANT_MAP.put(encName.replace(" ", "").toLowerCase(), enchantment);
        REVERSED_ENCHANT_MAP.put(enchantment, encName);
    }

    /**
     * Reads the item stack which is presented by the given configuration section.
     * @param section the configuration section
     * @return {@link PreparedItem}
     */
    public static PreparedItem read(ConfigurationSection section){
        return read(section, new PreparedItem());
    }

    /**
     * Reads the item stack which is presented by the given configuration section.
     * @param section the configuration section
     * @param item a {@link PreparedItem} which is used to store items data
     * @return the {@link PreparedItem} that was given
     */
    public static PreparedItem read(ConfigurationSection section, PreparedItem item){
        item.amount(section.getInt(Key.AMOUNT, 1));

        String material = section.getString(Key.MATERIAL);
        item.material(material == null ? Material.DIRT : Material.valueOf(material.toUpperCase()));

        item.damage((short) section.getInt(Key.DAMAGE));
        String name = section.getString(Key.NAME);
        if(name != null)
            item.name(ChatColor.translateAlternateColorCodes('&', name));

        item.lore(section.getStringList(Key.LORE)
                .stream()
                .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                .collect(Collectors.toList()));

        ConfigurationSection encsec = section.getConfigurationSection(Key.ENCHANT);
        if (encsec != null) {
            Set<String> keys = encsec.getKeys(false);
            for (String key : keys) item.enchants().put(ENCHANT_MAP.get(key.toLowerCase()), encsec.getInt(key));
        }

        section.getStringList(Key.FLAG).forEach(s -> item.flags().add(ItemFlag.valueOf(s.toUpperCase())));

        item.unbreakable(section.getBoolean(Key.UNBREAKABLE));

        ConfigurationSection attrsec = section.getConfigurationSection(Key.ATTRIBUTE);
        if (attrsec != null) {
            Attribute[] attrs = Attribute.values();
            for(Attribute attr : attrs){
                ConfigurationSection sec = attrsec.getConfigurationSection(attr.name().toLowerCase());
                if(sec == null) continue;
                Set<String> keys = sec.getKeys(false);
                for(String key : keys){
                    ConfigurationSection c = sec.getConfigurationSection(key);
                    if(c == null) continue;
                    String opt = c.getString("operation");
                    Modifier.Operation operation = opt == null ? Modifier.Operation.ADD_NUMBER :
                            Modifier.Operation.valueOf(opt.toUpperCase());
                    double amount = c.getDouble("amount");
                    String st = c.getString("slot");
                    EquipmentSlot slot = (st == null || st.trim().equalsIgnoreCase("all") ? null : EquipmentSlot.valueOf(st.toUpperCase()));
                    UUID id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
                    item.getItemModifiers().add(new ItemModifier(id, key, amount, operation, attr, slot));
                }
            }
        }
        return item;
    }

    /**
     * Writes the given item to config.
     * @param item the item
     * @return the config
     */
    public static YamlConfiguration write(ItemStack item){
        return write(item, new YamlConfiguration());
    }

    /**
     * Writes the given item to a configuration section.
     * @param item the item
     * @param section the section
     * @param <T> the sections data type
     * @return the section that was given
     */
    public static <T extends ConfigurationSection> T write(ItemStack item, T section){
        if(item != null){
            section.set(Key.MATERIAL, item.getType().name());
            section.set(Key.AMOUNT, item.getAmount());
            section.set(Key.DAMAGE, item.getDurability());
            ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                if(meta.hasDisplayName()) section.set(Key.NAME, meta.getDisplayName());

                section.set(Key.LORE, meta.getLore());

                if(meta.hasEnchants()) {
                    YamlConfiguration encsec = new YamlConfiguration();
                    meta.getEnchants().forEach((key, value) ->
                            encsec.set(REVERSED_ENCHANT_MAP.get(key).toLowerCase(), value));
                    section.set(Key.ENCHANT, encsec);
                }

                List<String> flags = meta.getItemFlags().stream().map(e -> e.name().toLowerCase()).collect(Collectors.toList());
                if(!flags.isEmpty()) section.set(Key.FLAG, flags);
            }
            ItemNBTHelper helper = ItemNBTHelper.of(item);

            if(helper.isUnbreakable()) section.set(Key.UNBREAKABLE, true);

            List<ItemModifier> modifiers = helper.getModifiers();
            YamlConfiguration attrsec = new YamlConfiguration();
            modifiers.forEach(modifier -> {
                YamlConfiguration sec = new YamlConfiguration();
                sec.set("operation", modifier.getOperation().name().toLowerCase());
                sec.set("amount", modifier.getAmount());
                EquipmentSlot es = modifier.getSlot();
                sec.set("slot", es == null ? "all" : es.name().toLowerCase());
                attrsec.set(modifier.getAttribute().name().toLowerCase()+"."+modifier.getName(), sec);
            });
            section.set(Key.ATTRIBUTE, attrsec);
        }
        return section;
    }
}
