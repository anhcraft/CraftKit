package dev.anhcraft.craftkit.internal.messengers;

import dev.anhcraft.craftkit.callbacks.bungee.*;
import dev.anhcraft.craftkit.common.callbacks.Callback;
import dev.anhcraft.craftkit.common.internal.CKPlugin;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.events.BungeeForwardEvent;
import dev.anhcraft.craftkit.internal.CKComponent;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.utils.BungeeUtil;
import dev.anhcraft.craftkit.utils.PlayerUtil;
import dev.anhcraft.jvmkit.utils.ArrayUtil;
import dev.anhcraft.jvmkit.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class BungeeUtilMessenger extends CKComponent implements PluginMessageListener {
    private final LinkedBlockingQueue<Callback> CALLBACK_QUEUE = new LinkedBlockingQueue<>();
    private final List<Pair<byte[], Callback>> TEMP_BG_QUEUE = new CopyOnWriteArrayList<>();
    private final List<Pair<byte[], Callback>> TEMP_CK_QUEUE = new CopyOnWriteArrayList<>();

    public BungeeUtilMessenger(CraftKit parent) {
        super(parent);

        CKProvider.TASK_HELPER.newAsyncTimerTask(() -> {
            if(!Bukkit.getOnlinePlayers().isEmpty()){
                Player p = Bukkit.getOnlinePlayers().iterator().next();
                TEMP_BG_QUEUE.forEach(x -> {
                    p.sendPluginMessage(parent, BungeeUtil.BC_CHANNEL_NAMESPACE, x.getFirst());
                    if(x.getSecond() != null) {
                        try {
                            CALLBACK_QUEUE.put(x.getSecond());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                TEMP_CK_QUEUE.forEach(x -> {
                    p.sendPluginMessage(parent, CKPlugin.CHANNEL_NAMESPACE, x.getFirst());
                    if(x.getSecond() != null) {
                        try {
                            CALLBACK_QUEUE.put(x.getSecond());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 0, 20);
    }


    public void sendCK(Player p, ByteArrayOutputStream stream){
        p.sendPluginMessage(getParent(), CKPlugin.CHANNEL_NAMESPACE, stream.toByteArray());
    }

    public void sendCK(Player p, ByteArrayOutputStream stream, Callback callback){
        sendCK(p, stream);
        try {
            CALLBACK_QUEUE.put(callback);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendCK(ByteArrayOutputStream stream){
        if(Bukkit.getOnlinePlayers().isEmpty()) TEMP_CK_QUEUE.add(new Pair<>(stream.toByteArray(), null));
        else sendCK(Bukkit.getOnlinePlayers().iterator().next(), stream);
    }

    public void sendCK(ByteArrayOutputStream stream, Callback callback){
        if(Bukkit.getOnlinePlayers().isEmpty()) TEMP_CK_QUEUE.add(new Pair<>(stream.toByteArray(), callback));
        else sendCK(Bukkit.getOnlinePlayers().iterator().next(), stream, callback);
    }

    public void sendBungee(Player p, ByteArrayOutputStream stream){
        p.sendPluginMessage(getParent(), BungeeUtil.BC_CHANNEL_NAMESPACE, stream.toByteArray());
    }

    public void sendBungee(Player p, ByteArrayOutputStream stream, Callback callback){
        sendBungee(p, stream);
        try {
            CALLBACK_QUEUE.put(callback);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendBungee(ByteArrayOutputStream stream){
        if(Bukkit.getOnlinePlayers().isEmpty()) TEMP_BG_QUEUE.add(new Pair<>(stream.toByteArray(), null));
        else sendBungee(Bukkit.getOnlinePlayers().iterator().next(), stream);
    }

    public void sendBungee(ByteArrayOutputStream stream, Callback callback){
        if(Bukkit.getOnlinePlayers().isEmpty()) TEMP_BG_QUEUE.add(new Pair<>(stream.toByteArray(), callback));
        else sendBungee(Bukkit.getOnlinePlayers().iterator().next(), stream, callback);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        DataInputStream i = new DataInputStream(new ByteArrayInputStream(message));
        try {
            if(channel.equals(BungeeUtil.BC_CHANNEL_NAMESPACE)) {
                String sc = i.readUTF();
                Callback br = CALLBACK_QUEUE.poll();
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

                byte[] arr = new byte[i.readShort()];
                i.readFully(arr);
                DataInputStream data = new DataInputStream(new ByteArrayInputStream(arr));
                BungeeForwardEvent ev = new BungeeForwardEvent(data);
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
