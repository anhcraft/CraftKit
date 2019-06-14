package dev.anhcraft.craftkit.internal.messengers;

import dev.anhcraft.craftkit.common.callbacks.Callback;
import dev.anhcraft.craftkit.callbacks.bungee.*;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.events.BungeeForwardEvent;
import dev.anhcraft.craftkit.utils.BungeeUtil;
import dev.anhcraft.craftkit.utils.PlayerUtil;
import dev.anhcraft.jvmkit.utils.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class BungeeUtilMessenger implements PluginMessageListener {
    protected static final LinkedBlockingQueue<Callback> CALLBACK_QUEUE = new LinkedBlockingQueue<>();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        var i = new DataInputStream(new ByteArrayInputStream(message));
        try {
            if(channel.equals(BungeeUtil.BC_CHANNEL_NAMESPACE)) {
                var sc = i.readUTF();
                var br = CALLBACK_QUEUE.poll();
                if (br != null) {
                    switch (sc) {
                        case "IP":
                            ((PlayerIPCallback) br).call(i.readUTF(), i.readUnsignedShort());
                            return;
                        case "PlayerCount":
                            ((PlayerCountCallback) br).call(i.readUTF(), i.readUnsignedShort());
                            return;
                        case "PlayerList":
                            ((PlayerListCallback) br).call(i.readUTF(), ArrayUtil.toList(i.readUTF().split(", ")));
                            return;
                        case "GetServers":
                            ((ServerListCallback) br).call(ArrayUtil.toList(i.readUTF().split(", ")));
                            return;
                        case "GetServer":
                            ((ServerNameCallback) br).call(i.readUTF());
                            return;
                        case "UUID":
                            ((PlayerIDCallback) br).call(UUID.fromString(i.readUTF()));
                            return;
                        case "UUIDOther":
                            ((ProxyPlayerIDCallback) br).call(i.readUTF(), UUID.fromString(i.readUTF()));
                            return;
                        case "ServerIP":
                            ((ServerIPCallback) br).call(i.readUTF(), i.readUTF(), i.readUnsignedShort());
                            return;
                    }
                }

                var arr = new byte[i.readShort()];
                i.readFully(arr);
                var data = new DataInputStream(new ByteArrayInputStream(arr));
                var ev = new BungeeForwardEvent(data);
                Bukkit.getPluginManager().callEvent(ev);
            } else if(channel.equals(CKPlugin.CHANNEL_NAMESPACE)){
                String sc = i.readUTF();
                switch (sc){
                    case "ChangeSkin":
                        String sign;
                        PlayerUtil.changeSkin(Bukkit.getPlayer(i.readUTF()),
                                new Skin(
                                        i.readUTF(),
                                        (sign = i.readUTF()).isEmpty() ? null : sign
                                ));
                        break;
                    case "RunServerCmdConsole":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), i.readUTF());
                        break;
                    case "RunServerCmdPlayer":
                        Bukkit.dispatchCommand(Bukkit.getPlayer(i.readUTF()), i.readUTF());
                        break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
