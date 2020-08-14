package dev.anhcraft.craftkit.internal;

import co.aikar.commands.PaperCommandManager;
import dev.anhcraft.craftkit.cb_common.NMSVersion;
import dev.anhcraft.craftkit.cb_common.internal.backend.BackendManager;
import dev.anhcraft.craftkit.cb_common.internal.backend.CBBlockBackend;
import dev.anhcraft.craftkit.chat.ActionBar;
import dev.anhcraft.craftkit.chat.Chat;
import dev.anhcraft.craftkit.common.internal.CKInfo;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.internal.Supervisor;
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
import dev.anhcraft.craftkit.utils.MaterialUtil;
import dev.anhcraft.jvmkit.utils.JarUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

public final class CraftKit extends JavaPlugin implements CKPlugin {
    public static final Chat DEFAULT_CHAT = new Chat("&6#craftkit:&f ");
    public static final Chat INFO_CHAT = new Chat("&6#craftkit:&b ");
    public static final Chat WARN_CHAT = new Chat("&6#craftkit:&c ");

    private void loadCkInfo() {
        InputStream in = getClass().getResourceAsStream("/ck_info.json");
        CKInfo.init(in, true);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMtInfo() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/ck_material.txt")));
            while(reader.ready()) {
                String line = reader.readLine();
                String[] p = line.split(" ");
                if(p.length == 3){
                    MaterialUtil.registerLegacyMaterial(p[0], Integer.parseInt(p[1]), p[2]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
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

        loadCkInfo();
        loadMtInfo();

        // load libraries
        libDir.mkdirs();
        handleNMSLib();
    }

    @Override
    public void onEnable() {
        // check updates
        checkUpdate();

        // register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ServerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        // register messaging channels
        getServer().getMessenger().registerOutgoingPluginChannel(this, BungeeUtil.BC_CHANNEL_NAMESPACE);
        getServer().getMessenger().registerIncomingPluginChannel(this, BungeeUtil.BC_CHANNEL_NAMESPACE, new BungeeUtilMessenger(this));
        getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL_NAMESPACE);
        getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL_NAMESPACE, new BungeeUtilMessenger(this));

        // plugin integrations
        if(PluginProvider.getProvider(VaultIntegration.class).init()) {
            INFO_CHAT.messageConsole("&aVault&f support enabled");
        }

        // start tasks
        CKProvider.TASK_HELPER.newTimerTask(new ArmorHandleTask(), 0, 20);

        PaperCommandManager pcm = new PaperCommandManager(this);
        pcm.registerCommand(new CKCommand());
        pcm.enableUnstableAPI("help");

        new Metrics(this, 6690);
    }

    @Override
    public void onDisable() {
        BackendManager.request(CBBlockBackend.class).orElseThrow(UnsupportedOperationException::new).stopThreadPool();
    }

    private void handleNMSLib() {
        NMSVersion nms = NMSVersion.current();
        File nmsFile = new File(libDir, "craftkit.nms." + nms.name() + "-" + CKInfo.getPluginVersion() + ".jar");
        if(!nmsFile.exists()) {
            INFO_CHAT.messageConsole("Downloading NMS library "+nms.name());
            if(CBLibProvider.downloadNMSLib(CKInfo.getPluginVersion(), nms, nmsFile)){
                DEFAULT_CHAT.messageConsole("Downloaded successfully!");
            } else {
                WARN_CHAT.messageConsole("Failed to download NMS library! The plugin will be disabled");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        try {
            JarUtil.loadJar(nmsFile, (URLClassLoader) getClassLoader());
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            WARN_CHAT.messageConsole("Failed to load library: "+ nmsFile.getName());
            e.printStackTrace();
            return;
        }
        DEFAULT_CHAT.messageConsole("Loaded library: "+ nmsFile.getName());
    }

    @Deprecated
    @NotNull
    public URLClassLoader getURLClassLoader(){
        Supervisor.checkAccess();
        return (URLClassLoader) getClassLoader();
    }
}
