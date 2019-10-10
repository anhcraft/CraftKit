package dev.anhcraft.craftkit.chat;

import dev.anhcraft.craftkit.common.chat.AbstractChat;
import dev.anhcraft.craftkit.common.utils.ChatUtil;
import org.jetbrains.annotations.NotNull;
import dev.anhcraft.jvmkit.utils.ArrayUtil;
import dev.anhcraft.jvmkit.utils.Condition;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.ConsoleCommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * This class is used for sending chat messages on the chat box.
 */
public class Chat extends AbstractChat {
    private static final Chat NO_PREFIX_CHAT = new Chat();
    ChatMessageType chatMessageType = ChatMessageType.CHAT;

    /**
     * Returns {@code Chat} whose prefix is empty.
     * @return chat
     */
    public static Chat noPrefix(){
        return NO_PREFIX_CHAT;
    }

    /**
     * Constructs an instance of {@code Chat} with empty chat prefix.
     */
    public Chat() {
        super("");
    }

    /**
     * Constructs an instance of {@code Chat} with the given chat prefix.
     * @param prefix the chat prefix (formatting codes that begun with ampersands ({@code &}) are supported)
     */
    public Chat(@Nullable String prefix) {
        super(prefix);
    }

    /**
     * Constructs an instance of {@code Chat} with the given prefix component.
     * @param prefixComponent the prefix component
     */
    public Chat(@Nullable BaseComponent prefixComponent) {
        super(prefixComponent);
    }

    @Override
    public Chat broadcast(@NotNull String msg) {
        Condition.argNotNull("msg", msg);
        Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
        String s = prefix + ChatUtil.formatColorCodes(msg);
        for (ProxiedPlayer p : players) p.sendMessage(s);
        return this;
    }

    @Override
    public Chat broadcast(@NotNull BaseComponent... components) {
        Condition.argNotNull("components", components);
        Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
        components = ArrayUtil.insert(components, prefixComponent, 0);
        for (ProxiedPlayer p : players) p.sendMessage(chatMessageType, components);
        return this;
    }

    @Override
    public Chat messageConsole(@NotNull String msg) {
        Condition.argNotNull("msg", msg);
        ProxyServer.getInstance().getConsole().sendMessage(prefix + ChatUtil.formatColorCodes(msg));
        return this;
    }

    @Override
    public Chat messageConsole(@NotNull BaseComponent... components) {
        Condition.argNotNull("components", components);
        StringBuilder s = new StringBuilder(prefix);
        for(@NotNull BaseComponent cpn : components) s.append(cpn.toLegacyText());
        ProxyServer.getInstance().getConsole().sendMessage(s.toString());
        return this;
    }

    /**
     * Broadcasts the given message to all players in a server.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param server a server
     * @param msg a message
     * @return this object
     */
    public Chat broadcast(@NotNull ServerInfo server, @NotNull String msg) {
        Condition.argNotNull("server", server);
        Condition.argNotNull("msg", msg);
        Collection<ProxiedPlayer> players = server.getPlayers();
        String s = prefix + ChatUtil.formatColorCodes(msg);
        for (ProxiedPlayer p : players) p.sendMessage(s);
        return this;
    }

    /**
     * Broadcasts given chat components to all players in a server.<br>
     * These components will be connected into a single message.
     * @param server a server
     * @param components components
     * @return this object
     */
    public Chat broadcast(@NotNull ServerInfo server, @NotNull BaseComponent... components) {
        Condition.argNotNull("server", server);
        Condition.argNotNull("components", components);
        Collection<ProxiedPlayer> players = server.getPlayers();
        components = ArrayUtil.insert(components, prefixComponent, 0);
        for (ProxiedPlayer p : players) p.sendMessage(chatMessageType, components);
        return this;
    }

    /**
     * Sends the given message to the target.<br>
     * Formatting codes that begun with ampersands ({@code &}) are supported.
     * @param target the target (can be a player, a command sender or the console, etc)
     * @param msg a message
     * @return this object
     */
    public Chat message(@NotNull CommandSender target, @NotNull String msg) {
        Condition.argNotNull("target", target);
        Condition.argNotNull("msg", msg);
        target.sendMessage(prefix + ChatUtil.formatColorCodes(msg));
        return this;
    }

    /**
     * Sends the given chat components to the target.<br>
     * These components will be connected into a single message.
     * @param target the target (can be a player, a command sender or the console, etc)
     * @param components components
     * @return this object
     */
    public Chat message(@NotNull CommandSender target, @NotNull BaseComponent... components) {
        Condition.argNotNull("target", target);
        Condition.argNotNull("components", components);
        if (target instanceof ProxiedPlayer) ((ProxiedPlayer) target).sendMessage(chatMessageType, components);
        else if(target instanceof ConsoleCommandSender) messageConsole(components);
        return this;
    }
}