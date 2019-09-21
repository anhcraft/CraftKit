package dev.anhcraft.craftkit.utils;

import dev.anhcraft.craftkit.callbacks.bungee.*;
import dev.anhcraft.craftkit.common.kits.skin.Skin;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import dev.anhcraft.craftkit.internal.messengers.BungeeUtilMessenger;
import dev.anhcraft.jvmkit.utils.Condition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Utility class to communicate with the BungeeCord proxy.
 */
public class BungeeUtil {
    /**
     * The namespace of the BungeeCord channel.
     */
    public static final String BC_CHANNEL_NAMESPACE = "BungeeCord";

    private static BungeeUtilMessenger messenger;

    /**
     * Requests the proxy to connect the given player to the specified server.
     * @param player the player who is playing on this server
     * @param server the server which the player will connect to
     */
    public static void connect(@NotNull Player player, @NotNull String server){
        Condition.argNotNull("player", player);
        Condition.argNotNull("server", server);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("Connect");
            out.writeUTF(server);
            messenger.sendBungee(player, stream);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("ConnectOther");
            out.writeUTF(player);
            out.writeUTF(server);
            messenger.sendBungee(stream);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("IP");
            messenger.sendBungee(player, stream, callback);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("PlayerCount");
            out.writeUTF(server);
            messenger.sendBungee(stream, callback);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("PlayerList");
            out.writeUTF(server);
            messenger.sendBungee(stream, callback);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("GetServers");
            messenger.sendBungee(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Messages the given player.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param player the name of that player
     * @param content the message content
     */
    public static void message(@NotNull String player, @NotNull String content){
        Condition.argNotNull("player", player);
        Condition.argNotNull("content", content);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("Message");
            out.writeUTF(player);
            out.writeUTF(ChatUtil.formatColorCodes(content));
            messenger.sendBungee(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Messages all players on this proxy.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("GetServer");
            messenger.sendBungee(stream, callback);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("UUID");
            messenger.sendBungee(player, stream, callback);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("UUIDOther");
            out.writeUTF(player);
            messenger.sendBungee(stream, callback);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("ServerIP");
            out.writeUTF(server);
            messenger.sendBungee(stream, callback);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests the proxy to kick the given player.
     * @param player the player who you want to kick
     * @param reason the reason why you kick him (formatting codes that begun with ampersands ({@code &}) are supported)
     */
    public static void kickPlayer(@NotNull String player, @NotNull String reason){
        Condition.argNotNull("player", player);
        Condition.argNotNull("reason", reason);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("KickPlayer");
            out.writeUTF(player);
            out.writeUTF(ChatUtil.formatColorCodes(reason));
            messenger.sendBungee(stream);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("Forward");
            out.writeUTF(server);
            out.writeUTF(channel);
            out.writeShort(data.length);
            out.write(data);
            messenger.sendBungee(stream);
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("ForwardToPlayer");
            out.writeUTF(player);
            out.writeUTF(channel);
            out.writeShort(data.length);
            out.write(data);
            messenger.sendBungee(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests the proxy to change the skin of a player.<br>
     * This method requires the proxy to have CraftKit installed.
     * @param player the player you want to change his skin
     * @param skin the new skin
     */
    public static void changeSkin(@NotNull String player, @NotNull Skin skin){
        Condition.argNotNull("player", player);
        Condition.argNotNull("skin", skin);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("ChangeSkin");
            out.writeUTF(player);
            out.writeUTF(skin.getValue());
            out.writeUTF(skin.getSignature() == null ? "" : skin.getSignature());
            messenger.sendCK(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Forces the console of the proxy to run the given command.
     * @param cmd the command to be executed
     */
    public static void runProxyCmdByConsole(@NotNull String cmd){
        Condition.argNotNull("cmd", cmd);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("RunProxyCmdConsole");
            out.writeUTF(cmd);
            messenger.sendCK(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Forces {@code player} to run the given proxy command.
     * @param player the player
     * @param cmd the command to be executed
     */
    public static void runProxyCmdByPlayer(@NotNull String player, @NotNull String cmd){
        Condition.argNotNull("player", player);
        Condition.argNotNull("cmd", cmd);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("RunProxyCmdPlayer");
            out.writeUTF(player);
            out.writeUTF(cmd);
            messenger.sendCK(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Forces the console of {@code server} to run the given command.
     * @param server the server
     * @param cmd the command to be executed
     */
    public static void runServerCmdByConsole(@NotNull String server, @NotNull String cmd){
        Condition.argNotNull("server", server);
        Condition.argNotNull("cmd", cmd);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("RunServerCmdConsole");
            out.writeUTF(server);
            out.writeUTF(cmd);
            messenger.sendCK(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Forces {@code player} to run the given server command.
     * @param player the player
     * @param cmd the command to be executed
     */
    public static void runServerCmdByPlayer(@NotNull String player, @NotNull String cmd){
        Condition.argNotNull("player", player);
        Condition.argNotNull("cmd", cmd);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF("RunServerCmdPlayer");
            out.writeUTF(player);
            out.writeUTF(cmd);
            messenger.sendCK(stream);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
