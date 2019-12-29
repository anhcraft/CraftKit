package dev.anhcraft.craftkit.abif;

import dev.anhcraft.confighelper.ConfigHelper;
import dev.anhcraft.confighelper.ConfigSchema;
import dev.anhcraft.confighelper.annotation.*;
import dev.anhcraft.confighelper.exception.InvalidValueException;
import dev.anhcraft.craftkit.BookGeneration;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.craftkit.helpers.ItemNBTHelper;
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
 * An object which holds all data of an {@link ItemStack}.<br>
 * This object is used during complex item-making works. It provides a method to get the final result as an {@link ItemStack}. It can also be serialized to store.<br>
 * Currently, {@link PreparedItem} does not support custom NBT tags.
 *
 * This is the clone of ABIF (https://github.com/anhcraft/ABIF/) which brings multi-version support.
 */
@Schema
public class PreparedItem implements Serializable {
    public static final ConfigSchema<PreparedItem> SCHEMA = ConfigSchema.of(PreparedItem.class);
    private static final long serialVersionUID = 7808305902298157946L;

    @Key(MODIFIERS)
    @Explanation("List of attribute modifiers")
    @IgnoreValue(ifNull = true)
    private List<ItemModifier> itemModifiers = new ArrayList<>();

    @Key(LORE)
    @Explanation("Item's lore")
    @IgnoreValue(ifNull = true)
    private List<String> lore = new ArrayList<>();

    @Key(FLAG)
    @PrettyEnum
    @Explanation("Items's flags that used to hide something")
    @IgnoreValue(ifNull = true)
    private List<ItemFlag> flags = new ArrayList<>();

    @Key(ENCHANT)
    @Explanation("Item's enchantments")
    @IgnoreValue(ifNull = true)
    private Map<Enchantment, Integer> enchants = new HashMap<>();

    @Key(MATERIAL)
    @Explanation("The material that make up this item")
    @IgnoreValue(ifNull = true)
    @PrettyEnum
    private Material material = Material.AIR;

    @Key(NAME)
    @Explanation("The name of this item")
    private String name;

    @Key(DAMAGE)
    @Explanation("The damaged value")
    private int damage;

    @Key(AMOUNT)
    @Explanation("The amount of items in this stack")
    private int amount = 1;

    @Key(UNBREAKABLE)
    @Explanation("Make the item unbreakable")
    private boolean unbreakable;

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
                lore.equals(that.lore) &&
                flags.equals(that.flags) &&
                enchants.equals(that.enchants) &&
                material == that.material &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lore, flags, enchants, material, name, damage, amount, unbreakable);
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
        return pi;
    }

    @Middleware(Middleware.Direction.CONFIG_TO_SCHEMA)
    private @Nullable Object conf2schema(ConfigSchema.Entry entry, @Nullable Object value) {
        if(value != null){
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
