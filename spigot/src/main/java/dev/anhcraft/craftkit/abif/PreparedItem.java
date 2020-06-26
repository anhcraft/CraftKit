package dev.anhcraft.craftkit.abif;

import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.ConfigSchema;
import dev.anhcraft.confighelper.EntryFilter;
import dev.anhcraft.confighelper.annotation.*;
import dev.anhcraft.confighelper.exception.InvalidValueException;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.craftkit.helpers.ItemNBTHelper;
import dev.anhcraft.craftkit.utils.MaterialUtil;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

import static dev.anhcraft.craftkit.abif.ABIF.Key.*;

/**
 * A prepared item stack that offers following features:<br>
 * <ul>
 *     <li>Easy to build</li>
 *     <li>Can be serialized as string</li>
 *     <li>Readable for most people</li>
 * </ul>
 */
@Schema
@Example({
        "material: diamond_sword",
        "name: '&2Emerald sword'",
        "enchant:",
        "  sharpness: 1",
        "flag:",
        "- hide_enchants"
})
@Example({
        "material: written_book",
        "amount: 1",
        "name: Steve's diary",
        "lore:",
        "- This is the diary of Steve",
        "flag:",
        "- hide_unbreakable",
        "modifiers:",
        "  '0':",
        "    name: '1'",
        "    amount: 1.0",
        "    operation: add_number",
        "    attr: generic_attack_damage",
        "    slot: off_hand",
        "meta:",
        "  type: book",
        "  book:",
        "    title: Steve's diary",
        "    author: Steve",
        "    generation: original",
        "    pages:",
        "    - My first day,...",
        "    - My second day,...",
        "    - The next day,...",
})
public class PreparedItem implements Serializable {
    public static final ConfigSchema<PreparedItem> SCHEMA = ConfigSchema.of(PreparedItem.class);
    private static final long serialVersionUID = 7808305902298157946L;
    private static final EntryFilter ENTRY_FILTER = ConfigHelper.newOptions().ignoreZero().ignoreEmptySection().ignoreEmptyArray().ignoreEmptyList();

    @Key(MATERIAL)
    @Explanation("The material that make up this item")
    @IgnoreValue(ifNull = true)
    @PrettyEnum
    private Material material = Material.AIR;

    @Key(AMOUNT)
    @Explanation("The amount of items in this stack")
    private int amount = 1;

    @Key(NAME)
    @Explanation("The name of this item")
    private String name;

    @Key(DAMAGE)
    @Explanation("The damaged value")
    private int damage;

    @Key(LORE)
    @Explanation("Item's lore")
    @IgnoreValue(ifNull = true)
    private List<String> lore = new ArrayList<>();

    @Key(ENCHANT)
    @Explanation("Item's enchantments")
    @IgnoreValue(ifNull = true)
    private Map<Enchantment, Integer> enchants = new HashMap<>();

    @Key(FLAG)
    @PrettyEnum
    @Explanation("Items's flags that used to hide something")
    @IgnoreValue(ifNull = true)
    private List<ItemFlag> flags = new ArrayList<>();

    @Key(UNBREAKABLE)
    @Explanation("Make the item unbreakable")
    private boolean unbreakable;

    @Key(MODIFIERS)
    @Explanation("List of attribute modifiers")
    @IgnoreValue(ifNull = true)
    @Example({
            "modifiers:",
            "  '0':",
            "    name: '1'",
            "    amount: 5.0",
            "    operation: add_number",
            "    attr: generic_attack_damage",
            "    slot: hand",
            "  '1':",
            "    name: '1'",
            "    amount: 2.0",
            "    operation: add_number",
            "    attr: generic_attack_damage",
            "    slot: off_hand"
    })
    private List<ItemModifier> itemModifiers = new ArrayList<>();

    @Key(META_TYPE)
    @Explanation("Item meta type")
    @PrettyEnum
    private MetaType metaType;

    @Key(META_POTION_TYPE)
    @Explanation({
            "Set the potion type",
            "Required item meta: potion"
    })
    @PrettyEnum
    private PotionType potionType;

    @Key(META_POTION_EXTENDED)
    @Explanation({
            "Set the 'extended' status",
            "Required item meta: potion"
    })
    private boolean potionExtended;

    @Key(META_POTION_UPGRADED)
    @Explanation({
            "Set the 'upgraded' status",
            "Required item meta: potion"
    })
    private boolean potionUpgraded;

    @Key(META_LEATHER_COLOR_R)
    @Explanation({
            "Set the leather color's red value",
            "Required item meta: leather"
    })
    private int leatherColorRed;

    @Key(META_LEATHER_COLOR_G)
    @Explanation({
            "Set the leather color's green value",
            "Required item meta: leather"
    })
    private int leatherColorGreen;

    @Key(META_LEATHER_COLOR_B)
    @Explanation({
            "Set the leather color's blue value",
            "Required item meta: leather"
    })
    private int leatherColorBlue;

    @Key(META_SKULL_OWNER)
    @Explanation({
            "Set the skull owner",
            "Required item meta: skull"
    })
    private String skullOwner;

    @Key(META_BOOK_TITLE)
    @Explanation({
            "Set the title of the book",
            "Required item meta: book"
    })
    private String bookTitle;

    @Key(META_BOOK_AUTHOR)
    @Explanation({
            "Set the author of the book",
            "Required item meta: book"
    })
    private String bookAuthor;

    @Key(META_BOOK_GENERATION)
    @Explanation({
            "Set the generation of the book",
            "Required item meta: book"
    })
    @PrettyEnum
    private BookMeta.Generation bookGeneration;

    @Key(META_BOOK_PAGES)
    @Explanation({
            "Set the pages of the book",
            "Required item meta: book"
    })
    private List<String> bookPages;

    /**
     * Makes a {@link PreparedItem} from the given {@link ItemStack}.
     * @param itemStack the item stack
     * @return {@link PreparedItem}
     */
    @NotNull
    public static PreparedItem of(@Nullable ItemStack itemStack){
        PreparedItem pi = new PreparedItem();
        if(itemStack != null) {
            pi.material = itemStack.getType();
            pi.amount = itemStack.getAmount();
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null) {
                if (meta instanceof Damageable) pi.damage = ((Damageable) meta).getDamage();
                if(meta.hasDisplayName()) pi.name = meta.getDisplayName();
                if(meta.hasLore()) pi.lore = meta.getLore();
                pi.flags = new ArrayList<>(meta.getItemFlags());
                pi.enchants = meta.getEnchants();
                if(meta instanceof PotionMeta) pi.metaType = MetaType.POTION;
                else if(meta instanceof LeatherArmorMeta) pi.metaType = MetaType.LEADER;
                else if(meta instanceof SkullMeta) pi.metaType = MetaType.SKULL;
                else if(meta instanceof BookMeta) pi.metaType = MetaType.BOOK;
                if(pi.metaType != null){
                    pi.metaType.getOnLoad().accept(pi, meta);
                }
            }
            ItemNBTHelper helper = ItemNBTHelper.of(itemStack);
            pi.unbreakable = helper.isUnbreakable();
            pi.itemModifiers.addAll(helper.getModifiers());
        }

        return pi;
    }

    /**
     * Makes a {@link PreparedItem} from the given {@link ConfigurationSection}
     * @param section configuration section
     * @return {@link PreparedItem}
     * @throws InvalidValueException if having issues from the configuration
     */
    @NotNull
    public static PreparedItem of(@NotNull ConfigurationSection section) throws InvalidValueException {
        return ConfigHelper.readConfig(section, PreparedItem.SCHEMA, new PreparedItem());
    }

    @NotNull
    public Material material() {
        return material;
    }

    public void material(@Nullable Material type) {
        this.material = type == null ? Material.AIR : type;
    }

    @Nullable
    public String name() {
        return name;
    }

    public void name(@Nullable String name) {
        this.name = name;
    }

    public int damage() {
        return damage;
    }

    public void damage(int damage) {
        this.damage = damage;
    }

    public int amount() {
        return amount;
    }

    public void amount(int amount) {
        this.amount = amount;
    }

    public boolean unbreakable() {
        return unbreakable;
    }

    public void unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    @NotNull
    public List<String> lore() {
        return lore;
    }

    public void lore(@Nullable List<String> lore) {
        if(lore == null) this.lore.clear();
        else this.lore = lore;
    }

    @NotNull
    public List<ItemFlag> flags() {
        return flags;
    }

    public void flags(@Nullable List<ItemFlag> flags) {
        if(flags == null) this.flags.clear();
        else this.flags = flags;
    }

    @NotNull
    public Map<Enchantment, Integer> enchants() {
        return enchants;
    }

    public void enchants(@Nullable Map<Enchantment, Integer> enchants) {
        if(enchants == null) this.enchants.clear();
        else this.enchants = enchants;
    }

    @NotNull
    public List<ItemModifier> itemModifiers() {
        return itemModifiers;
    }

    public void itemModifiers(@Nullable List<ItemModifier> itemModifiers) {
        if(itemModifiers == null) this.itemModifiers.clear();
        else this.itemModifiers = itemModifiers;
    }

    @Nullable
    public MetaType metaType() {
        return metaType;
    }

    public void metaType(@Nullable MetaType metaType) {
        this.metaType = metaType;
    }

    public boolean potionExtended() {
        return potionExtended;
    }

    public void potionExtended(boolean potionExtended) {
        this.potionExtended = potionExtended;
    }

    public boolean potionUpgraded() {
        return potionUpgraded;
    }

    public void potionUpgraded(boolean potionUpgraded) {
        this.potionUpgraded = potionUpgraded;
    }

    @Nullable
    public PotionType potionType() {
        return potionType;
    }

    public void potionType(@Nullable PotionType potionType) {
        this.potionType = potionType;
    }

    public int leatherColorRed() {
        return leatherColorRed;
    }

    public void leatherColorRed(int leatherColorRed) {
        this.leatherColorRed = leatherColorRed;
    }

    public int leatherColorGreen() {
        return leatherColorGreen;
    }

    public void leatherColorGreen(int leatherColorGreen) {
        this.leatherColorGreen = leatherColorGreen;
    }

    public int leatherColorBlue() {
        return leatherColorBlue;
    }

    public void leatherColorBlue(int leatherColorBlue) {
        this.leatherColorBlue = leatherColorBlue;
    }

    @Nullable
    public String skullOwner() {
        return skullOwner;
    }

    public void skullOwner(@Nullable String skullOwner) {
        this.skullOwner = skullOwner;
    }

    @Nullable
    public String bookTitle() {
        return bookTitle;
    }

    public void bookTitle(@Nullable String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Nullable
    public String bookAuthor() {
        return bookAuthor;
    }

    public void bookAuthor(@Nullable String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Nullable
    public BookMeta.Generation bookGeneration() {
        return bookGeneration;
    }

    public void bookGeneration(@Nullable BookMeta.Generation bookGeneration) {
        this.bookGeneration = bookGeneration;
    }

    @Nullable
    public List<String> bookPages() {
        return bookPages;
    }

    public void bookPages(@Nullable List<String> bookPages) {
        this.bookPages = bookPages;
    }

    /**
     * Builds a new item stack.
     * @return {@link ItemStack}
     */
    @NotNull
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, (short) damage);
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            if(name != null) {
                meta.setDisplayName(name);
            }
            if(!lore.isEmpty()) {
                meta.setLore(lore);
            }
            if(!flags.isEmpty()) {
                flags.stream().filter(Objects::nonNull).forEach(meta::addItemFlags);
            }
            if(!enchants.isEmpty()) {
                for (Map.Entry<Enchantment, Integer> e : enchants.entrySet())
                    meta.addEnchant(e.getKey(), e.getValue(), true);
            }
            if(metaType != null){
                metaType.getOnSave().accept(this, meta);
            }
            item.setItemMeta(meta);
        }
        ItemNBTHelper helper = ItemNBTHelper.of(item);
        if(itemModifiers != null && !itemModifiers.isEmpty()) helper.setModifiers(itemModifiers);
        helper.setUnbreakable(unbreakable);
        return helper.save();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreparedItem that = (PreparedItem) o;
        return damage == that.damage &&
                amount == that.amount &&
                unbreakable == that.unbreakable &&
                potionExtended == that.potionExtended &&
                potionUpgraded == that.potionUpgraded &&
                leatherColorRed == that.leatherColorRed &&
                leatherColorGreen == that.leatherColorGreen &&
                leatherColorBlue == that.leatherColorBlue &&
                itemModifiers.equals(that.itemModifiers) &&
                lore.equals(that.lore) &&
                flags.equals(that.flags) &&
                enchants.equals(that.enchants) &&
                material == that.material &&
                Objects.equals(name, that.name) &&
                metaType == that.metaType &&
                potionType == that.potionType &&
                Objects.equals(skullOwner, that.skullOwner) &&
                Objects.equals(bookTitle, that.bookTitle) &&
                Objects.equals(bookAuthor, that.bookAuthor) &&
                bookGeneration == that.bookGeneration &&
                Objects.equals(bookPages, that.bookPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemModifiers, lore, flags, enchants, material, name, damage, amount, unbreakable, metaType, potionType, potionExtended, potionUpgraded, leatherColorRed, leatherColorGreen, leatherColorBlue, skullOwner, bookTitle, bookAuthor, bookGeneration, bookPages);
    }

    /**
     * Clones this object.
     * @return a new instance of {@link PreparedItem}
     */
    @NotNull
    public PreparedItem duplicate(){
        return copyTo(new PreparedItem());
    }

    /**
     * @deprecated Use {@link #copyTo(PreparedItem)} instead.
     */
    @Deprecated
    @NotNull
    public PreparedItem merge(@NotNull PreparedItem pi){
        return copyTo(pi);
    }

    /**
     * Copies all properties of this {@link PreparedItem} to the given one.
     * @param pi another object
     * @return the given {@link PreparedItem}
     */
    @NotNull
    public PreparedItem copyTo(@NotNull PreparedItem pi){
        Condition.argNotNull("pi", pi);
        pi.name = name;
        pi.damage = damage;
        pi.amount = amount;
        pi.unbreakable = unbreakable;
        pi.material = material;
        pi.enchants.putAll(enchants);
        pi.flags.addAll(flags);
        for (ItemModifier m : itemModifiers) {
            pi.itemModifiers.add(m.duplicate());
        }
        pi.lore.addAll(lore);
        pi.metaType = metaType;
        pi.skullOwner = skullOwner;
        pi.potionType = potionType;
        pi.potionUpgraded = potionUpgraded;
        pi.potionExtended = potionExtended;
        pi.leatherColorRed = leatherColorRed;
        pi.leatherColorGreen = leatherColorGreen;
        pi.leatherColorBlue = leatherColorBlue;
        pi.bookTitle = bookTitle;
        pi.bookPages = bookPages;
        pi.bookAuthor = bookAuthor;
        pi.bookGeneration = bookGeneration;
        return pi;
    }

    /**
     * Saves this item to configuration.
     * @param conf the configuration
     * @param <T> data type
     * @return configuration
     */
    @NotNull
    public <T extends ConfigurationSection> T saveTo(@NotNull T conf){
        ConfigHelper.writeConfig(conf, PreparedItem.SCHEMA, this, ENTRY_FILTER);
        return conf;
    }

    @Middleware(Middleware.Direction.CONFIG_TO_SCHEMA)
    private @Nullable Object conf2schema(ConfigSchema.Entry entry, @Nullable Object value) {
        if(value != null){
            if(entry.getKey().equals(MATERIAL) && value instanceof String) {
                return MaterialUtil.modernize((String) value);
            }
            if(entry.getKey().equals(MODIFIERS)) {
                ConfigurationSection cs = (ConfigurationSection) value;
                List<ItemModifier> bullets = new ArrayList<>();
                for (String s : cs.getKeys(false)) {
                    try {
                        bullets.add(ConfigHelper.readConfig(cs.getConfigurationSection(s), ItemModifier.SCHEMA));
                    } catch (InvalidValueException e) {
                        e.printStackTrace();
                    }
                }
                return bullets;
            } else if(entry.getKey().equals(ENCHANT)){
                ConfigurationSection cs = (ConfigurationSection) value;
                Map<Enchantment, Integer> map = new HashMap<>();
                for(String s : cs.getKeys(false)){
                    map.put(ABIF.getEnchant(s), cs.getInt(s));
                }
                return map;
            }
        }
        return value;
    }

    @Middleware(Middleware.Direction.SCHEMA_TO_CONFIG)
    private @Nullable Object schema2conf(ConfigSchema.Entry entry, @Nullable Object value) {
        if(value != null){
            if(entry.getKey().equals(MODIFIERS)) {
                ConfigurationSection parent = new YamlConfiguration();
                int i = 0;
                for(ItemModifier modifier : (List<ItemModifier>) value){
                    YamlConfiguration c = new YamlConfiguration();
                    ConfigHelper.writeConfig(c, ItemModifier.SCHEMA, modifier);
                    parent.set(String.valueOf(i++), c);
                }
                return parent;
            } else if(entry.getKey().equals(ENCHANT)){
                ConfigurationSection parent = new YamlConfiguration();
                Map<Enchantment, Integer> map = (Map<Enchantment, Integer>) value;
                for(Map.Entry<Enchantment, Integer> x : map.entrySet()){
                    parent.set(x.getKey().getName().toLowerCase(), x.getValue());
                }
                return parent;
            }
        }
        return value;
    }
}
