package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.cb_common.internal.CBProvider;
import dev.anhcraft.craftkit.cb_common.internal.CBServerService;
import dev.anhcraft.craftkit.events.ServerReloadEvent;
import dev.anhcraft.craftkit.events.ServerStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class ServerListener implements Listener {
    private static CBServerService SERVICE = CBProvider.getService(CBServerService.class).orElseThrow(UnsupportedOperationException::new);
    private int reloadCounter = SERVICE.getReloadCount();
    private boolean isStopping = false;

    @EventHandler
    public void disablePlugin(PluginDisableEvent event){
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
