package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.common.helpers.Selector;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A helper who can collaborate with you in manipulating item stacks.
 */
public class ItemHelper extends Selector<ItemStack> {
    private ItemMeta meta;

    /**
     * Constructs an {@code ItemHelper} object which selects the given item stack.
     * @param itemStack the item stack
     * @return ItemHelper
     */
    @NotNull
    public static ItemHelper of(@NotNull ItemStack itemStack){
        ItemHelper i = new ItemHelper();
        i.select(itemStack);
        return i;
    }

    @Override
    protected boolean onSelected(@NotNull ItemStack target) {
        meta = target.getItemMeta();
        return true;
    }

    /**
     * Applies all changes to the stack and returns it.
     * @return the target item stack
     */
    @NotNull
    public ItemStack save(){
        getTarget().setItemMeta(meta);
        return getTarget();
    }

    /**
     * Returns the name of the stack.
     * @return stack's name
     */
    @Nullable
    public String getName() {
        return meta.getDisplayName();
    }

    /**
     * Returns the name of the stack.<br>
     * If the name is empty, the argument {@code ifEmpty} will be used instead.
     * @param ifEmpty the alternative name
     * @return stack's name
     */
    @Contract("null -> null")
    public String getName(@Nullable String ifEmpty) {
        return meta.hasDisplayName() ? meta.getDisplayName() : ifEmpty;
    }

    /**
     * Sets a new name for the stack.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param name the new name
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper setName(@Nullable String name) {
        meta.setDisplayName(name == null ? null : ChatUtil.formatColorCodes(name)); // it is better to set null directly instead of calling another method
        return this;
    }

    /**
     * Adds the given enchantment into the stack.
     * @param enchant the enchantment
     * @param level the level of the enchantment
     * @return this object
     */
    @Contract("_, _ -> this")
    public ItemHelper addEnchant(@NotNull Enchantment enchant, int level) {
        Condition.argNotNull("enchant", enchant);
        Condition.check(level > 0, "`level` must be higher than zero");
        meta.addEnchant(enchant, level, true);
        return this;
    }

    /**
     * Removes an existing enchantment.
     * @param enchant the enchantment which you want to remove
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper removeEnchant(@NotNull Enchantment enchant) {
        Condition.argNotNull("enchant", enchant);
        meta.removeEnchant(enchant);
        return this;
    }

    /**
     * Returns all existing enchantments on the stack.
     * @return an immutable map of enchantments with enchantment as keys and their level as values
     */
    @NotNull
    public Map<Enchantment, Integer> getEnchants() {
        return meta.getEnchants();
    }

    /**
     * Returns the level of the given enchantment.
     * @param enchant the enchantment
     * @return the level of the enchantment (or zero if the enchantment did not exist
     */
    public int getEnchant(@NotNull Enchantment enchant) {
        Condition.argNotNull("enchant", enchant);
        return meta.getEnchantLevel(enchant);
    }

    /**
     * Checks if the stack contains the given enchantment.
     * @param enchant the enchantment
     * @return {@code true} if it does or {@code false} if not
     */
    public boolean hasEnchant(@NotNull Enchantment enchant) {
        Condition.argNotNull("enchant", enchant);
        return meta.hasEnchant(enchant);
    }

    /**
     * Adds a lore line into the stack.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param line the lore line
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper addLoreLine(@NotNull String line) {
        Condition.argNotNull("line", line);
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(ChatUtil.formatColorCodes(line));
        meta.setLore(lore);
        return this;
    }

    /**
     * Adds multiple lore lines into the stack.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param lines an array of lore lines
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper addLoreLines(@NotNull String... lines) {
        Condition.argNotNull("lines", lines);
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        for(String line : lines) lore.add(ChatUtil.formatColorCodes(line));
        meta.setLore(lore);
        return this;
    }

    /**
     * Adds multiple lore lines into the stack.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param lines a list of lore lines
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper addLoreLines(@NotNull List<String> lines) {
        Condition.argNotNull("lines", lines);
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.addAll(ChatUtil.formatColorCodes(lines));
        meta.setLore(lore);
        return this;
    }

    /**
     * Overrides the content of a lore line.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param index the index of the line
     * @param content the new content
     * @return this object
     */
    @Contract("_, _ -> this")
    public ItemHelper setLoreLine(int index, @NotNull String content) {
        Condition.argNotNull("content", content);
        Condition.check(index >= 0, "`index` must be higher than or equals with zero");
        if(meta.hasLore()) {
            List<String> lore = meta.getLore();
            Condition.check(index < lore.size(), "`index` is out of bounds");
            lore.set(index, content);
            meta.setLore(lore);
        }
        return this;
    }

    /**
     * Sets the lore of the stack.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param lore the new lore (if this is null, the lore will be empty)
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper setLore(@Nullable List<String> lore) {
        meta.setLore(lore == null ? null : ChatUtil.formatColorCodes(lore));
        return this;
    }

    /**
     * Removes a certain lore line.
     * @param index the index of the line (from zero)
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper removeLoreLine(int index) {
        Condition.check(index >= 0, "`index` must be higher than or equals with zero");
        if(meta.hasLore()) {
            List<String> lore = meta.getLore();
            Condition.check(index < lore.size(), "`index` is out of bounds");
            lore.remove(index);
            meta.setLore(lore);
        }
        return this;
    }

    /**
     * Removes the lore of the stack.
     */
    public void removeLore() {
        meta.setLore(null);
    }

    /**
     * Returns the lore of the stack.
     * @return the lore
     */
    @NotNull
    public List<String> getLore() {
        return meta.hasLore() ? meta.getLore() : new ArrayList<>();
    }

    /**
     * Adds given flags into the stack.
     * @param flags flags to be added
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper addFlag(@NotNull ItemFlag... flags) {
        Condition.argNotNull("flags", flags);
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Removes given flags out of the stack.
     * @param flags flags to be removed
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper removeFlags(@NotNull ItemFlag... flags) {
        Condition.argNotNull("flags", flags);
        meta.removeItemFlags(flags);
        return this;
    }

    /**
     * Checks if the given flag exists on the stack.
     * @param flag the flag
     * @return {@code true} if it does, {@code false} otherwise
     */
    public boolean hasFlag(@NotNull ItemFlag flag) {
        Condition.argNotNull("flag", flag);
        return meta.hasItemFlag(flag);
    }

    /**
     * Returns all existing flags on the stack.
     * @return an immutable set of flags
     */
    @NotNull
    public Set<ItemFlag> getFlags() {
        return meta.getItemFlags();
    }

    /**
     * Sets the durability of the stack.
     * @param durability new durability
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper setDurability(short durability) {
        getTarget().setDurability(durability);
        return this;
    }

    /**
     * Returns the durability of the stack.
     * @return the durability
     */
    public short getDurability() {
        return getTarget().getDurability();
    }

    /**
     * Overrides the type of the stack.
     * @param type new type
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper setType(@Nullable Material type) {
        getTarget().setType(type == null ? Material.AIR : type);
        return this;
    }

    /**
     * Returns the material type of the stack.
     * @return stack's type
     */
    @NotNull
    public Material getType() {
        return getTarget().getType();
    }

    /**
     * Sets the amount of items in the stack.
     * @param amount new amount
     * @return this object
     */
    @Contract("_ -> this")
    public ItemHelper setAmount(int amount) {
        getTarget().setAmount(amount);
        return this;
    }

    /**
     * Returns the amount of items.
     * @return the amount
     */
    public int getAmount() {
        return getTarget().getAmount();
    }
}
