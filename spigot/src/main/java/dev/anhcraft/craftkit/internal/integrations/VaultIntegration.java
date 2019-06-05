package dev.anhcraft.craftkit.internal.integrations;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultIntegration extends PluginProvider<JavaPlugin> {
    protected static Permission perm;
    protected static Economy eco;
    protected static Chat chat;

    @Override
    public boolean init(){
        var sv = Bukkit.getServer();
        if(!sv.getPluginManager().isPluginEnabled("Vault")) return false;

        var sm = sv.getServicesManager();

        RegisteredServiceProvider<Economy> ecoPr = sm.getRegistration(Economy.class);
        if(ecoPr != null) {
            var p = ecoPr.getProvider();
            if(p.isEnabled()) eco = p;
        }

        RegisteredServiceProvider<Permission> permPr = sm.getRegistration(Permission.class);
        if(permPr != null)  {
            var p = permPr.getProvider();
            if(p.isEnabled()) perm = p;
        }

        RegisteredServiceProvider<Chat> chatPr = sm.getRegistration(Chat.class);
        if(chatPr != null) {
            var p = chatPr.getProvider();
            if(p.isEnabled()) chat = p;
        }

        return (perm != null) || (eco != null) || (chat != null);
    }
}
