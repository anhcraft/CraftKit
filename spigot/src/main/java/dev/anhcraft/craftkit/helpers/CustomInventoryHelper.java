package dev.anhcraft.craftkit.helpers;

import dev.anhcraft.craftkit.cb_common.callbacks.inventory.SlotCallback;
import dev.anhcraft.craftkit.cb_common.internal.CBCustomInventoryService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.kits.inventory.CustomInventory;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.jvmkit.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A {@link CustomInventory} helper.
 */
public class CustomInventoryHelper extends AbstractPluginHelper {
    private static final CBCustomInventoryService SERVICE = CBProvider.getService(CBCustomInventoryService.class).orElseThrow(UnsupportedOperationException::new);

    /**
     * Constructs an instance of {@code CustomInventoryHelper} with the given plugin.
     * @param plugin the plugin
     */
    public CustomInventoryHelper(@NotNull Plugin plugin) {
        super(plugin);
    }

    /**
     * Creates a custom inventory.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param size the number of slots
     * @param title the inventory title
     * @return the inventory
     */
    public CustomInventory create(int size, @Nullable String title){
        return create(null, size, title);
    }

    /**
     * Creates a custom inventory.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param holder the holder
     * @param size the number of slots
     * @param title the inventory title
     * @return the inventory
     */
    public CustomInventory create(@Nullable InventoryHolder holder, int size, @Nullable String title){
        size = (int) MathUtil.nextMultiple(size, 9);
        title = (title == null ? null : ChatUtil.formatColorCodes(title));
        CustomInventory inv = SERVICE.create(holder, size, title);
        Bukkit.getPluginManager().registerEvents(inv, plugin);
        return inv;
    }

    /**
     * Creates a custom inventory whose slots are unmodifiable.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param size the number of slots
     * @param title the inventory title
     * @return the inventory
     */
    public CustomInventory createUnmodifiable(int size, @Nullable String title){
        CustomInventory ci = create(null, size, title);
        ci.addContentCallback(SlotCallback.PREVENT_MODIFY);
        return ci;
    }
}
