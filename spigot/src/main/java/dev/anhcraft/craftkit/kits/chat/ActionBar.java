package dev.anhcraft.craftkit.kits.chat;

import dev.anhcraft.craftkit.common.utils.ChatUtil;
import org.jetbrains.annotations.NotNull;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used for sending chat messages on the action bar.
 */
public class ActionBar extends Chat {
    private static final ActionBar NO_PREFIX_ACTIONBAR = new ActionBar();

    /**
     * Returns {@code ActionBar} whose prefix is empty.
     * @return ActionBar object
     */
    public static ActionBar noPrefix(){
        return NO_PREFIX_ACTIONBAR;
    }

    /**
     * Constructs an instance of {@code ActionBar} with empty chat prefix.
     */
    public ActionBar() {
        super("");
        chatMessageType = ChatMessageType.ACTION_BAR;
    }

    /**
     * Constructs an instance of {@code ActionBar} with the given chat prefix.
     * @param prefix the chat prefix (formatting codes that begun with ampersands ({@code &}) are supported)
     */
    public ActionBar(@Nullable String prefix) {
        super(prefix);
        chatMessageType = ChatMessageType.ACTION_BAR;
    }

    /**
     * Constructs an instance of {@code ActionBar} with the given prefix component.
     * @param prefixComponent the prefix component
     */
    public ActionBar(@Nullable BaseComponent prefixComponent) {
        super(prefixComponent);
        chatMessageType = ChatMessageType.ACTION_BAR;
    }

    @Override
    public ActionBar broadcast(@NotNull String msg) {
        broadcast(new TextComponent(TextComponent.fromLegacyText(ChatUtil.formatColorCodes(msg))));
        return this;
    }

    @Override
    public ActionBar messageConsole(@NotNull String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionBar messageConsole(@NotNull BaseComponent... components) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionBar broadcast(@NotNull World world, @NotNull String msg) {
        broadcast(world, new TextComponent(
                TextComponent.fromLegacyText(ChatUtil.formatColorCodes(msg))));
        return this;
    }

    @Override
    public ActionBar message(@NotNull CommandSender target, @NotNull String msg) {
        if(target instanceof ConsoleCommandSender) throw new UnsupportedOperationException();
        message(target, new TextComponent(
                TextComponent.fromLegacyText(ChatUtil.formatColorCodes(msg))));
        return this;
    }
}