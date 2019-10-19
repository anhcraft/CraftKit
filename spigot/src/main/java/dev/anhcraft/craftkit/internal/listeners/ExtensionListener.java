package dev.anhcraft.craftkit.internal.listeners;

import dev.anhcraft.craftkit.CraftExtension;
import dev.anhcraft.craftkit.common.internal.Supervisor;
import org.bukkit.event.Listener;

public class ExtensionListener implements Listener {
    private CraftExtension extension;

    public ExtensionListener(CraftExtension extension) {
        Supervisor.checkAccess();
        this.extension = extension;
    }
}
