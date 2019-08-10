package dev.anhcraft.craftkit.internal;

import dev.anhcraft.craftkit.common.internal.CKConfig;
import dev.anhcraft.craftkit.common.internal.CKInfo;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.helpers.ConfigHelper;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.listeners.CKChannelListener;
import dev.anhcraft.craftkit.kits.chat.ActionBar;
import dev.anhcraft.craftkit.kits.chat.Chat;
import dev.anhcraft.jvmkit.utils.FileUtil;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

public final class CraftKit extends Plugin implements CKPlugin {
    public static final Chat DEFAULT_CHAT = new Chat("&6#craftkit:&f ");
    public static final Chat INFO_CHAT = new Chat("&6#craftkit:&b ");
    public static final Chat WARN_CHAT = new Chat("&6#craftkit:&c ");
    public static CraftKit instance;

    @Override
    public void onEnable() {
        instance = this;

        // put things to the internal provider
        CKProvider.CHAT_NO_PREFIX = Chat.noPrefix();
        CKProvider.ACTIONBAR_NO_PREFIX = ActionBar.noPrefix();
        CKProvider.TASK_HELPER = new TaskHelper(this);

        // load info
        CKInfo.init(getClass().getResourceAsStream("/ck_info.json"));

        // init files and load the configuration
        ROOT_DIR.mkdirs();
        INFO_CHAT.messageConsole("Loading configuration files...");
        FileUtil.init(CONFIG_FILE, getClass().getResourceAsStream("/config.yml"));
        new ConfigHelper(CONFIG_FILE).loadTo(CONFIG);

        // check config version
        if(CKConfig.CONFIG_VERSION < CKInfo.getConfigVersion()){
            try {
                WARN_CHAT.messageConsole("Your configuration is outdated! ("+CKConfig.CONFIG_VERSION+")");
                INFO_CHAT.messageConsole("Attempting to upgrade the configuration...")
                        .messageConsole("Creating a backup version of the configuration....");
                OLD_CONFIG_FILE.createNewFile();
                FileUtil.copy(CONFIG_FILE, OLD_CONFIG_FILE);
                INFO_CHAT.messageConsole("Creating the new configuration file...");
                FileUtil.write(CONFIG_FILE, getClass().getResourceAsStream("/config.yml"));
                INFO_CHAT.messageConsole("Loading the new configuration...");
                new ConfigHelper(CONFIG_FILE).loadTo(CONFIG);
                DEFAULT_CHAT.messageConsole("Your configuration file has been updated successfully!")
                        .messageConsole("The old one is named `"+OLD_CONFIG_FILE.getName() +"` which you can check it later and transfer old values to the new configuration");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        // check updates
        checkUpdate();

        // register listeners
        INFO_CHAT.messageConsole("Registering internal listeners...");
        getProxy().getPluginManager().registerListener(this, new CKChannelListener());

        // register messaging channels
        INFO_CHAT.messageConsole("Registering messaging channels...");
        getProxy().registerChannel(CHANNEL_NAMESPACE);
    }
}
