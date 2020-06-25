package dev.anhcraft.craftkit.abif;

import dev.anhcraft.craftkit.attribute.Attribute;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.craftkit.attribute.Modifier;
import dev.anhcraft.craftkit.helpers.ItemNBTHelper;
import dev.anhcraft.craftkit.utils.MaterialUtil;
import dev.anhcraft.jvmkit.utils.Condition;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the clone of ABIF (https://github.com/anhcraft/ABIF/) which brings multi-version support.
 */
public class ABIF {
    static class Key{
        final static String MATERIAL = "material";
        final static String AMOUNT = "amount";
        final static String DAMAGE = "damage";
        final static String NAME = "name";
        final static String LORE = "lore";
        final static String ENCHANT = "enchant";
        final static String FLAG = "flag";
        final static String UNBREAKABLE = "unbreakable";
        final static String MODIFIERS = "modifiers";
        final static String ATTRIBUTE = "attr";
        final static String META_TYPE = "meta.type";
        final static String META_POTION_TYPE = "meta.potion.type";
        final static String META_POTION_EXTENDED = "meta.potion.extended";
        final static String META_POTION_UPGRADED = "meta.potion.upgraded";
        final static String META_LEATHER_COLOR_R = "meta.leather.color_r";
        final static String META_LEATHER_COLOR_G = "meta.leather.color_g";
        final static String META_LEATHER_COLOR_B = "meta.leather.color_b";
        final static String META_SPAWN_EGG_ENTITY = "meta.spawn_egg.entity";
        final static String META_SKULL_OWNER = "meta.skull.owner";
        final static String META_BOOK_AUTHOR = "meta.book.author";
        final static String META_BOOK_TITLE = "meta.book.title";
        final static String META_BOOK_GENERATION = "meta.book.generation";
        final static String META_BOOK_PAGES = "meta.book.pages";
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
        registerEnchant("soul speed", "SOUL_SPEED");
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
    public static void registerEnchant(@NotNull String encName, @NotNull Enchantment enchantment){
        Condition.argNotNull("encName", encName);
        Condition.argNotNull("enchantment", enchantment);
        ENCHANT_MAP.put(encName.toLowerCase(), enchantment);
        ENCHANT_MAP.put(encName.replace(" ", "").toLowerCase(), enchantment);
        REVERSED_ENCHANT_MAP.put(enchantment, encName);
    }

    @Nullable
    public static Enchantment getEnchant(@NotNull String encName){
        Condition.argNotNull("encName", encName);
        return ENCHANT_MAP.get(encName.toLowerCase());
    }

    /**
     * Reads the item stack which is presented by the given configuration section.
     * @param section the configuration section
     * @return {@link PreparedItem}
     */
    @Deprecated
    @NotNull
    public static PreparedItem read(@NotNull ConfigurationSection section){
        return read(section, new PreparedItem());
    }

    /**
     * Reads the item stack which is presented by the given configuration section.
     * @param section the configuration section
     * @param item a {@link PreparedItem} which is used to store items data
     * @return the {@link PreparedItem} that was given
     */
    @Deprecated
    @NotNull
    public static PreparedItem read(@NotNull ConfigurationSection section, @NotNull PreparedItem item){
        Condition.argNotNull("section", section);
        Condition.argNotNull("item", item);
        item.amount(section.getInt(Key.AMOUNT, 1));

        String material = section.getString(Key.MATERIAL);
        item.material(MaterialUtil.parse(material).orElse(Material.DIRT));

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
            Map<Enchantment, Integer> enchants = item.enchants();
            Set<String> keys = encsec.getKeys(false);
            for (String key : keys) {
                enchants.put(ENCHANT_MAP.get(key.toLowerCase()), encsec.getInt(key));
            }
        }

        List<ItemFlag> flags = item.flags();
        section.getStringList(Key.FLAG).forEach(s -> flags.add(ItemFlag.valueOf(s.toUpperCase())));

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
                    item.itemModifiers().add(new ItemModifier(id, key, amount, operation, attr, slot));
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
    @Deprecated
    @NotNull
    public static YamlConfiguration write(@Nullable ItemStack item){
        return write(item, new YamlConfiguration());
    }

    /**
     * Writes the given item to a configuration section.
     * @param item the item
     * @param section the section
     * @param <T> the sections data type
     * @return the section that was given
     */
    @Deprecated
    @NotNull
    public static <T extends ConfigurationSection> T write(@Nullable ItemStack item, @NotNull T section){
        Condition.argNotNull("section", section);
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
