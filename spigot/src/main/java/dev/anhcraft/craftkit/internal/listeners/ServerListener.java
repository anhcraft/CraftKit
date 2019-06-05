package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.internal.CBServerService;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.internal.assistants.CKAssistant;
import dev.anhcraft.craftkit.events.ServerReloadEvent;
import dev.anhcraft.craftkit.events.ServerStopEvent;
import dev.anhcraft.craftkit.internal.CraftKit;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class ServerListener implements Listener {
    private static CBServerService SERVICE = CBProvider.getService(CBServerService.class).orElseThrow();
    private static int reloadCounter = SERVICE .getReloadCount();
    private static boolean isStopping = false;

    @EventHandler
    public void enablePlugin(PluginEnableEvent event){
        CKProvider.TASK_HELPER.newAsyncTask(CraftKit.instance::doIndex);
    }

    @EventHandler
    public void disablePlugin(PluginDisableEvent event){
        CKProvider.TASK_HELPER.newAsyncTask(() -> {
            CKAssistant.cleanIndexes();
            CraftKit.instance.doIndex();
        });
        if(isStopping) return;
        int reloadCount = SERVICE.getReloadCount();
        boolean isRunning = SERVICE.isRunning();
        if(isRunning){
            if(reloadCounter < reloadCount){
                ServerReloadEvent ev = new ServerReloadEvent();
                Bukkit.getPluginManager().callEvent(ev);
                reloadCounter = reloadCount;
            }
        } else {
            ServerStopEvent ev = new ServerStopEvent();
            Bukkit.getPluginManager().callEvent(ev);
            isStopping = true;
        }
    }
}
