package dev.anhcraft.craftkit.builders;

import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.jvmkit.builders.Builder;
import dev.anhcraft.jvmkit.utils.CollectionUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You can make item stacks easier with this builder!
 */
public class ItemBuilder implements Builder<ItemStack> {
    private final List<String> lore = new ArrayList<>();
    private final List<ItemFlag> flags = new ArrayList<>();
    private final Map<Enchantment, Integer> enchants = new HashMap<>();
    private Material type;
    private String name;
    private short durability;
    private int amount = 1;

    /**
     * Constructs an instance of {@code ItemBuilder}.
     * @param type the type of the item stack which you are going to build
     */
    public ItemBuilder(@Nullable Material type){
        this.type = (type == null ? Material.AIR : type);
    }

    /**
     * Sets a name for this stack.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param name the name
     * @return this object
     */
    public ItemBuilder name(String name){
        this.name = name;
        return this;
    }

    /**
     * Sets the amount of items.
     * @param amount the amount
     * @return this object
     */
    public ItemBuilder amount(int amount){
        this.amount = amount;
        return this;
    }

    /**
     * Sets the durability (damage value).
     * @param durability the durability
     * @return this object
     */
    public ItemBuilder durability(short durability){
        this.durability = durability;
        return this;
    }

    /**
     * Adds a lore line.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param line the lore line
     * @return this object
     */
    public ItemBuilder lore(String line){
        lore.add(line);
        return this;
    }

    /**
     * Adds a bunch of lore lines.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param lines lore lines
     * @return this object
     */
    public ItemBuilder lore(List<String> lines){
        if(lines != null) lore.addAll(lines);
        return this;
    }

    /**
     * Adds an enchantment.
     * @param enchant the enchantment
     * @param level the level
     * @return this object
     */
    public ItemBuilder enchant(Enchantment enchant, int level){
        if(enchant != null && level > 0) this.enchants.put(enchant, level);
        return this;
    }

    /**
     * Adds an item flag
     * @param flag the flag
     * @return this object
     */
    public ItemBuilder flag(ItemFlag flag){
        flags.add(flag);
        return this;
    }

    /**
     * Builds this item stack and returns it.
     * @return the item stack
     */
    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(type, amount, durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.formatColorCodes(name));
        meta.setLore(ChatUtil.formatColorCodes(lore));
        meta.addItemFlags(CollectionUtil.toArray(flags.stream().filter(Objects::nonNull)
                .collect(Collectors.toList()), ItemFlag.class));
        for (Map.Entry<Enchantment, Integer> e : enchants.entrySet()) meta.addEnchant(e.getKey(), e.getValue(), true);
        item.setItemMeta(meta);
        return item;
    }
}
