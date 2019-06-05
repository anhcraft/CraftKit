package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.internal.assistants.CKCleaner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {
    @EventHandler
    public void unload(WorldUnloadEvent event){
        CKProvider.TASK_HELPER.newAsyncTask(() -> CKCleaner.clean(o -> o.equals(event.getWorld())));
    }
}
