package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.internal.assistants.CKCleaner;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {
    @EventHandler
    public void quit(PlayerDisconnectEvent event){
        CKProvider.TASK_HELPER.newAsyncTask(() -> CKCleaner.clean(o -> o.equals(event.getPlayer()) || o.equals(event.getPlayer().getUniqueId())));
    }
}
