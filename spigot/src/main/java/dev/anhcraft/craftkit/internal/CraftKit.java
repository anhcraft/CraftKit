package dev.anhcraft.craftkit.internal;

import dev.anhcraft.craftkit.chat.ActionBar;
import dev.anhcraft.craftkit.chat.Chat;
import dev.anhcraft.craftkit.common.internal.CKInfo;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.integrations.PluginProvider;
import dev.anhcraft.craftkit.internal.integrations.VaultIntegration;
import dev.anhcraft.craftkit.internal.listeners.EntityListener;
import dev.anhcraft.craftkit.internal.listeners.InventoryListener;
import dev.anhcraft.craftkit.internal.listeners.PlayerListener;
import dev.anhcraft.craftkit.internal.listeners.ServerListener;
import dev.anhcraft.craftkit.internal.messengers.BungeeUtilMessenger;
import dev.anhcraft.craftkit.internal.tasks.ArmorHandleTask;
import dev.anhcraft.craftkit.utils.BungeeUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CraftKit extends JavaPlugin implements CKPlugin {
    public static final Chat DEFAULT_CHAT = new Chat("&6#craftkit:&f ");
    public static final Chat INFO_CHAT = new Chat("&6#craftkit:&b ");
    public static final Chat WARN_CHAT = new Chat("&6#craftkit:&c ");

    @Override
    public void onEnable() {
        try{
            Class.forName("org.spigotmc.SpigotConfig");
        } catch(ClassNotFoundException e) {
            getLogger().info(() -> "CraftKit can only work on Spigot-based servers (Spigot, PaperSpigot, etc)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // put things to the internal provider
        CKProvider.CHAT_NO_PREFIX = Chat.noPrefix();
        CKProvider.ACTIONBAR_NO_PREFIX = ActionBar.noPrefix();
        CKProvider.TASK_HELPER = new TaskHelper(this);

        // load info
        CKInfo.init(getClass().getResourceAsStream("/ck_info.json"));

        // check updates
        checkUpdate(CKPlugin.SPIGOT_RESOURCE_ID);

        // register listeners
        INFO_CHAT.messageConsole("Registering internal listeners...");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ServerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        // register messaging channels
        INFO_CHAT.messageConsole("Registering messaging channels...");
        getServer().getMessenger().registerOutgoingPluginChannel(this, BungeeUtil.BC_CHANNEL_NAMESPACE);
        getServer().getMessenger().registerIncomingPluginChannel(this, BungeeUtil.BC_CHANNEL_NAMESPACE, new BungeeUtilMessenger(this));
        getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL_NAMESPACE);
        getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL_NAMESPACE, new BungeeUtilMessenger(this));

        // plugin integrations
        INFO_CHAT.messageConsole("Hooking soft-dependencies...");
        if(PluginProvider.getProvider(VaultIntegration.class).init()) INFO_CHAT.messageConsole("&aVault&f support enabled");

        // start tasks
        INFO_CHAT.messageConsole("Starting tasks...");
        CKProvider.TASK_HELPER.newTimerTask(new ArmorHandleTask(), 0, 20);
    }
}
