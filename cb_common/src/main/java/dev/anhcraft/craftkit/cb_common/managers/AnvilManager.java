package dev.anhcraft.craftkit.cb_common.managers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.AnvilCallback;
import dev.anhcraft.craftkit.cb_common.callbacks.inventory.AnvilSlotCallback;
import dev.anhcraft.craftkit.cb_common.internal.CBAnvilService;
import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.inventory.AnvilSlot;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class manages anvils which are created by CraftKit.
 */
public class AnvilManager implements Listener {
    private static final CBAnvilService SERVICE = CBProvider.getService(CBAnvilService.class).orElseThrow(UnsupportedOperationException::new);
    private final Map<Integer, ItemStack> slotItem = new HashMap<>();
    private final Multimap<AnvilSlot, AnvilSlotCallback> slotCallback = HashMultimap.create();
    private final List<Inventory> activeAnvils = new CopyOnWriteArrayList<>();
    private final List<AnvilCallback> inv = new ArrayList<>();
    private boolean destroyed;

    /**
     * Constructs an instance of {@code AnvilManager} with the given plugin.
     * @param plugin the plugin who owns this manager
     */
    public AnvilManager(@NotNull Plugin plugin){
        Condition.argNotNull("plugin", plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Puts given item into an anvil slot.
     * @param slot the slot
     * @param itemStack the item
     */
    public void putItem(@NotNull AnvilSlot slot, ItemStack itemStack){
        Condition.check(!destroyed, "AnvilManager was destroyed");
        Condition.argNotNull("slot", slot);
        slotItem.put(slot.getId(), itemStack);
    }

    /**
     * Adds given callback for an anvil slot
     * @param slot the slot
     * @param callback the callback
     */
    public void addSlotCallback(@NotNull AnvilSlot slot, @NotNull AnvilSlotCallback callback){
        Condition.check(!destroyed, "AnvilManager was destroyed");
        Condition.argNotNull("slot", slot);
        Condition.argNotNull("callback", callback);
        slotCallback.put(slot, callback);
    }

    /**
     * Adds given callback for anvils
     * @param callback callback
     */
    public void addAnvilCallback(@NotNull AnvilCallback callback){
        Condition.check(!destroyed, "AnvilManager was destroyed");
        Condition.argNotNull("callback", callback);
        inv.add(callback);
    }

    /**
     * Opens a new anvil instance for the given player
     * @param player the player
     */
    public void open(@NotNull Player player){
        Condition.argNotNull("player", player);
        Condition.check(!destroyed, "AnvilManager was destroyed");
        SERVICE.open(player, inventory -> {
            slotItem.forEach(inventory::setItem);
            activeAnvils.add(inventory);
        });
    }

    @EventHandler
    private void onClick(InventoryClickEvent event){
        if(!activeAnvils.contains(event.getClickedInventory()) || !(event.getWhoClicked() instanceof Player)) return;
        final AnvilManager ins = this;
        final Player p = (Player) event.getWhoClicked();
        slotCallback.get(AnvilSlot.getById(event.getRawSlot())).forEach(callback -> callback.click(event, p, ins, event.getClickedInventory()));
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event){
        if(!activeAnvils.contains(event.getInventory()) || !(event.getPlayer() instanceof Player)) return;
        final AnvilManager ins = this;
        final Player p = (Player) event.getPlayer();
        inv.forEach(callback -> callback.close(event, p, ins, event.getInventory()));
        event.getInventory().clear();
        activeAnvils.remove(event.getInventory());
    }

    /**
     * Destroys this manager.
     */
    public void destroy(){
        destroyed = true;
        activeAnvils.forEach(Inventory::clear);
        activeAnvils.stream()
                .flatMap((Function<Inventory, Stream<HumanEntity>>) inventory -> inventory.getViewers().stream())
                .forEach(HumanEntity::closeInventory);
        activeAnvils.clear();
        slotItem.clear();
        slotCallback.clear();
        inv.clear();
        HandlerList.unregisterAll(this);
    }
}
