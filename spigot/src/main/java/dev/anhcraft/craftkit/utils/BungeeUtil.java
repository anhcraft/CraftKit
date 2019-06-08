package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.callbacks.Callback;
import dev.anhcraft.craftkit.callbacks.bungee.*;
import dev.anhcraft.craftkit.cb_common.lang.enumeration.NMSVersion;
import dev.anhcraft.craftkit.common.internal.CKProvider;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.craftkit.internal.CraftKit;
import dev.anhcraft.craftkit.internal.messengers.BungeeUtilMessenger;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.Condition;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to communicate with the BungeeCord proxy.
 */
public class BungeeUtil extends BungeeUtilMessenger {
    /**
     * The namespace of the BungeeCord channel.
     */
    public static final String BC_CHANNEL_NAMESPACE = NMSVersion.getNMSVersion().isNewerOrSame(NMSVersion.v1_13_R1) ? "bungeecord:main" : "BungeeCord";

    // any requests that need at least one player can be queued here
    private static final List<Pair<byte[], Callback>> TEMP_QUEUE = new ArrayList<>();

    static {
        CKProvider.TASK_HELPER.newAsyncTimerTask(() -> {
            if(!TEMP_QUEUE.isEmpty() && !Bukkit.getOnlinePlayers().isEmpty()){
                var p = Bukkit.getOnlinePlayers().iterator().next();
                TEMP_QUEUE.forEach(x -> {
                    p.sendPluginMessage(CraftKit.instance, BC_CHANNEL_NAMESPACE, x.getLeft());
                    if(x.getRight() != null) {
                        try {
                            CALLBACK_QUEUE.put(x.getRight());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 0, 20); // this task can be async xD
    }

    private static void send(Player p, ByteArrayOutputStream stream){
        p.sendPluginMessage(CraftKit.instance, BC_CHANNEL_NAMESPACE, stream.toByteArray());
    }
    
    private static void send(Player p, ByteArrayOutputStream stream, Callback callback){
        send(p, stream);
        try {
            CALLBACK_QUEUE.put(callback);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void send(ByteArrayOutputStream stream){
        if(Bukkit.getOnlinePlayers().isEmpty()) TEMP_QUEUE.add(Pair.of(stream.toByteArray(), null));
        else send(Bukkit.getOnlinePlayers().iterator().next(), stream);
    }
    
    private static void send(ByteArrayOutputStream stream, Callback callback){
        if(Bukkit.getOnlinePlayers().isEmpty()) TEMP_QUEUE.add(Pair.of(stream.toByteArray(), callback));
        else send(Bukkit.getOnlinePlayers().iterator().next(), stream, callback);
    }

    /**
     * Requests the proxy to connect the given player to the specified server.
     * @param player the player who is playing on this server
     * @param server the server which the player will connect to
     */
    public static void connect(@NotNull Player player, @NotNull String server){
        Condition.argNotNull("player", player);
        Condition.argNotNull("server", server);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("Connect");
            out.writeUTF(server);
            send(player, stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests the proxy to connect the given player to the specified server.
     * @param player the player who is playing on the network
     * @param server the server which the player will connect to
     */
    public static void connect(@NotNull String player, @NotNull String server){
        Condition.argNotNull("player", player);
        Condition.argNotNull("server", server);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("ConnectOther");
            out.writeUTF(player);
            out.writeUTF(server);
            send(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the IP address of given player.
     * @param player the player who is playing on this server
     * @param callback the callback which will give you the result
     */
    public static void getIP(@NotNull Player player, @NotNull PlayerIPCallback callback){
        Condition.argNotNull("player", player);
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("IP");
            send(player, stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts the total number of online players in the given server.
     * @param server the server
     * @param callback the callback which will give you the result
     */
    public static void countPlayer(@NotNull String server, @NotNull PlayerCountCallback callback){
        Condition.argNotNull("server", server);
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("PlayerCount");
            out.writeUTF(server);
            send(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts the total number of online players on this proxy.
     * @param callback the callback which will give you the result
     */
    public static void countProxyPlayer(@NotNull PlayerCountCallback callback){
        countPlayer("ALL", callback);
    }

    /**
     * Lists all players who are playing on the given server.
     * @param server the server
     * @param callback the callback which will give you the result
     */
    public static void listPlayers(@NotNull String server, @NotNull PlayerListCallback callback){
        Condition.argNotNull("server", server);
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("PlayerList");
            out.writeUTF(server);
            send(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lists all players who are playing on this proxy.
     * @param callback the callback which will give you the result
     */
    public static void listProxyPlayers(@NotNull PlayerListCallback callback){
        listPlayers("ALL", callback);
    }

    /**
     * Lists all servers which are under this proxy.
     * @param callback the callback which will give you the result
     */
    public static void listServers(@NotNull ServerListCallback callback){
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("GetServers");
            send(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Messages the given player.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.
     * @param player the name of that player
     * @param content the message content
     */
    public static void message(@NotNull String player, @NotNull String content){
        Condition.argNotNull("player", player);
        Condition.argNotNull("content", content);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("Message");
            out.writeUTF(player);
            out.writeUTF(ChatUtil.formatColorCodes(content));
            send(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Messages all players on this proxy.<br>
     * Formatting codes which are begun with ampersands ({@code &}) are supported.
     * @param content the message content
     */
    public static void messageAll(@NotNull String content){
        message("ALL", content);
    }

    /**
     * Gets the name of this server.
     * @param callback the callback which will give you the result
     */
    public static void getServerName(@NotNull ServerNameCallback callback){
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("GetServer");
            send(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the unique id of given player.
     * @param player the player
     * @param callback the callback which will give you the result
     */
    public static void getUniqueId(@NotNull Player player, @NotNull PlayerIDCallback callback){
        Condition.argNotNull("player", player);
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("UUID");
            send(player, stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the unique id of given player.
     * @param player the player who is playing on this proxy
     * @param callback the callback which will give you the result
     */
    public static void getUniqueId(@NotNull String player, @NotNull PlayerIDCallback callback){
        Condition.argNotNull("player", player);
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("UUIDOther");
            out.writeUTF(player);
            send(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the IP address of the given server.
     * @param server the server
     * @param callback the callback which will give you the result
     */
    public static void getServerIP(@NotNull String server, @NotNull ServerIPCallback callback){
        Condition.argNotNull("server", server);
        Condition.argNotNull("callback", callback);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("ServerIP");
            out.writeUTF(server);
            send(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests the proxy to kick the given player.
     * @param player the player who you want to kick
     * @param reason the reason why you kick him (formatting codes which are begun with ampersands ({@code &}) are supported)
     */
    public static void kickPlayer(@NotNull String player, @NotNull String reason){
        Condition.argNotNull("player", player);
        Condition.argNotNull("reason", reason);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("KickPlayer");
            out.writeUTF(player);
            out.writeUTF(ChatUtil.formatColorCodes(reason));
            send(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the given data to a server which is under this proxy.
     * @param server the server which you want to send to
     * @param channel the sub channel
     * @param data the data
     */
    public static void forward(@NotNull String server, @NotNull String channel, @NotNull byte[] data){
        Condition.argNotNull("server", server);
        Condition.argNotNull("channel", channel);
        Condition.argNotNull("data", data);
        try {
            var stream  = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("Forward");
            out.writeUTF(server);
            out.writeUTF(channel);
            out.writeShort(data.length);
            out.write(data);
            send(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the given data to all servers under this proxy.
     * @param channel the sub channel
     * @param data the data
     */
    public static void forwardAll(@NotNull String channel, @NotNull byte[] data){
        forward("ALL", channel, data);
    }

    /**
     * Sends the given data to all servers which are currently online.
     * @param channel the sub channel
     * @param data the data
     */
    public static void forwardOnline(@NotNull String channel, @NotNull byte[] data){
        forward("ONLINE", channel, data);
    }

    /**
     * Sends the given data to a player.
     * @param player the player who is playing on this proxy.
     * @param channel the sub channel
     * @param data the data
     */
    public static void forwardDataPlayer(@NotNull String player, @NotNull String channel, @NotNull byte[] data){
        Condition.argNotNull("player", player);
        Condition.argNotNull("channel", channel);
        Condition.argNotNull("data", data);
        try {
            var stream = new ByteArrayOutputStream();
            var out = new DataOutputStream(stream);
            out.writeUTF("ForwardToPlayer");
            out.writeUTF(player);
            out.writeUTF(channel);
            out.writeShort(data.length);
            out.write(data);
            send(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
