package dev.anhcraft.craftkit.internal;

import dev.anhcraft.craftkit.chat.ActionBar;
import dev.anhcraft.craftkit.chat.Chat;
import dev.anhcraft.craftkit.common.internal.CKInfo;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.helpers.TaskHelper;
import dev.anhcraft.craftkit.internal.listeners.CKChannelListener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;

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
        InputStream in = getClass().getResourceAsStream("/ck_info.json");
        CKInfo.init(in, false);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
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
