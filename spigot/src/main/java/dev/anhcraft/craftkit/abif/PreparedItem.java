package dev.anhcraft.craftkit.abif;

import dev.anhcraft.config.ConfigDeserializer;
import dev.anhcraft.config.ConfigSerializer;
import dev.anhcraft.config.adapters.TypeAdapter;
import dev.anhcraft.config.annotations.*;
import dev.anhcraft.config.bukkit.BukkitConfigProvider;
import dev.anhcraft.config.bukkit.struct.YamlConfigSection;
import dev.anhcraft.config.schema.ConfigSchema;
import dev.anhcraft.config.schema.SchemaScanner;
import dev.anhcraft.config.struct.ConfigSection;
import dev.anhcraft.craftkit.abif.adapters.EnchantmentAdapter;
import dev.anhcraft.craftkit.abif.adapters.MaterialAdapter;
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
 * A prepared item stack that offers following features:<br>
 * <ul>
 *     <li>Easy to build</li>
 *     <li>Can be serialized as string</li>
 *     <li>Readable for most people</li>
 * </ul>
 */
@Configurable
public class PreparedItem implements Serializable {
    public static final ConfigSchema SCHEMA = SchemaScanner.scanConfig(PreparedItem.class);
    private static final long serialVersionUID = 7808305902298157946L;
    private static final ConfigSerializer serializer;
    private static final ConfigDeserializer deserializer;

    static {
        TypeAdapter<?>[] adapters = new TypeAdapter<?>[]{
                new MaterialAdapter(),
                new EnchantmentAdapter()
        };

        serializer = BukkitConfigProvider.YAML.createSerializer();
        serializer.registerTypeAdapter(Material.class, adapters[0]);
        serializer.registerTypeAdapter(Enchantment.class, adapters[1]);

        deserializer = BukkitConfigProvider.YAML.createDeserializer();
        deserializer.registerTypeAdapter(Material.class, adapters[0]);
        deserializer.registerTypeAdapter(Enchantment.class, adapters[1]);
    }

    @Setting
    @Path(MATERIAL)
    @Description("The material that make up this item")
    @Validation(notNull = true, silent = true)
    private Material material = Material.AIR;

    @Setting
    @Path(AMOUNT)
    @Description("The amount of items in this stack")
    private int amount = 1;

    @Setting
    @Path(NAME)
    @Description("The name of this item")
    private String name;

    @Setting
    @Path(DAMAGE)
    @Description("The damaged value")
    private int damage;

    @Setting
    @Path(LORE)
    @Description("Item's lore")
    @Validation(notNull = true, silent = true)
    private List<String> lore = new ArrayList<>();

    @Setting
    @Path(ENCHANT)
    @Description("Item's enchantments")
    @Validation(notNull = true, silent = true)
    private Map<Enchantment, Integer> enchants = new HashMap<>();

    @Setting
    @Path(FLAG)
    @Description("Items's flags that used to hide something")
    @Validation(notNull = true, silent = true)
    private List<ItemFlag> flags = new ArrayList<>();

    @Setting
    @Path(UNBREAKABLE)
    @Description("Make the item unbreakable")
    private boolean unbreakable;

    @Setting
    @Path(MODIFIERS)
    @Description("List of attribute modifiers")
    @Validation(notNull = true, silent = true)
    private List<ItemModifier> itemModifiers = new ArrayList<>();

    @Setting
    @Path(META_TYPE)
    @Description("Item meta type")
    private MetaType metaType;

    @Setting
    @Path(META_POTION_TYPE)
    @Description({
            "Set the potion type",
            "Required item meta: potion"
    })
    private PotionType potionType;

    @Setting
    @Path(META_POTION_EXTENDED)
    @Description({
            "Set the 'extended' status",
            "Required item meta: potion"
    })
    private boolean potionExtended;

    @Setting
    @Path(META_POTION_UPGRADED)
    @Description({
            "Set the 'upgraded' status",
            "Required item meta: potion"
    })
    private boolean potionUpgraded;

    @Setting
    @Path(META_LEATHER_COLOR_R)
    @Description({
            "Set the leather color's red value",
            "Required item meta: leather"
    })
    private int leatherColorRed;

    @Setting
    @Path(META_LEATHER_COLOR_G)
    @Description({
            "Set the leather color's green value",
            "Required item meta: leather"
    })
    private int leatherColorGreen;

    @Setting
    @Path(META_LEATHER_COLOR_B)
    @Description({
            "Set the leather color's blue value",
            "Required item meta: leather"
    })
    private int leatherColorBlue;

    @Setting
    @Path(META_SKULL_OWNER)
    @Description({
            "Set the skull owner",
            "Required item meta: skull"
    })
    private String skullOwner;

    @Setting
    @Path(META_BOOK_TITLE)
    @Description({
            "Set the title of the book",
            "Required item meta: book"
    })
    private String bookTitle;

    @Setting
    @Path(META_BOOK_AUTHOR)
    @Description({
            "Set the author of the book",
            "Required item meta: book"
    })
    private String bookAuthor;

    @Setting
    @Path(META_BOOK_GENERATION)
    @Description({
            "Set the generation of the book",
            "Required item meta: book"
    })
    private BookMeta.Generation bookGeneration;

    @Setting
    @Path(META_BOOK_PAGES)
    @Description({
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
     * Makes a {@link PreparedItem} from the given {@link ConfigSection}
     * @param section configuration section
     * @return {@link PreparedItem}
     * @throws Exception if having issues from the configuration
     */
    @NotNull
    public static PreparedItem of(@NotNull ConfigSection section) throws Exception {
        return deserializer.transformConfig(PreparedItem.SCHEMA, section, new PreparedItem());
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
     * @return configuration
     * @exception Exception if any error occured
     */
    @NotNull
    public ConfigSection saveTo(@NotNull ConfigSection conf) throws Exception {
        return serializer.transformConfig(SCHEMA, conf, this);
    }
}
