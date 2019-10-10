package dev.anhcraft.craftkit.abif;

import dev.anhcraft.craftkit.helpers.ItemNBTHelper;
import dev.anhcraft.craftkit.attribute.ItemModifier;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

/**
 * An object which holds all data of an {@link ItemStack}.<br>
 * This object is used during complex item-making works. It provides a method to get the final result as an {@link ItemStack}. It can also be serialized to store.<br>
 * Currently, {@link PreparedItem} does not support custom NBT tags.
 *
 * This is the clone of ABIF (https://github.com/anhcraft/ABIF/) which brings multi-version support.
 */
public class PreparedItem implements Serializable {
    private static final long serialVersionUID = 7808305902298157946L;

    private List<ItemModifier> itemModifiers = new ArrayList<>();
    private List<String> lore = new ArrayList<>();
    private Set<ItemFlag> flags = new HashSet<>();
    private Map<Enchantment, Integer> enchants = new HashMap<>();
    private Material material = Material.AIR;
    private String name;
    private int damage;
    private int amount = 1;
    private boolean unbreakable;

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
                pi.name = meta.getDisplayName();
                pi.lore = meta.getLore();
                pi.flags = meta.getItemFlags();
                pi.enchants = meta.getEnchants();
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

    @Nullable
    public List<String> lore() {
        return lore;
    }

    public void lore(@Nullable List<String> lore) {
        this.lore = lore;
    }

    @Nullable
    public Set<ItemFlag> flags() {
        return flags;
    }

    public void flags(@Nullable Set<ItemFlag> flags) {
        this.flags = flags;
    }

    @Nullable
    public Map<Enchantment, Integer> enchants() {
        return enchants;
    }

    public void enchants(@Nullable Map<Enchantment, Integer> enchants) {
        this.enchants = enchants;
    }

    @Nullable
    public List<ItemModifier> getItemModifiers() {
        return itemModifiers;
    }

    public void setItemModifiers(@Nullable List<ItemModifier> itemModifiers) {
        this.itemModifiers = itemModifiers;
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
            if(name != null)
                meta.setDisplayName(name);
            if(lore != null && !lore.isEmpty())
                meta.setLore(lore);
            if(flags != null && !flags.isEmpty())
                flags.stream().filter(Objects::nonNull).forEach(meta::addItemFlags);
            if(enchants != null && !enchants.isEmpty())
                for (Map.Entry<Enchantment, Integer> e : enchants.entrySet())
                    meta.addEnchant(e.getKey(), e.getValue(), true);
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
                Objects.equals(lore, that.lore) &&
                Objects.equals(flags, that.flags) &&
                Objects.equals(enchants, that.enchants) &&
                material == that.material &&
                Objects.equals(name, that.name);
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
        return merge(new PreparedItem());
    }

    /**
     * Merges this object into the given one.
     * @param pi another object
     * @return {@link PreparedItem}
     */
    @NotNull
    public PreparedItem merge(@NotNull PreparedItem pi){
        Condition.argNotNull("pi", pi);
        pi.name = name;
        pi.damage = damage;
        pi.amount = amount;
        pi.unbreakable = unbreakable;
        pi.material = material;
        pi.enchants.putAll(enchants);
        pi.flags.addAll(flags);
        for(ItemModifier m : itemModifiers){
            pi.itemModifiers.add(m.duplicate());
        }
        pi.lore.addAll(lore);
        return pi;
    }
}
